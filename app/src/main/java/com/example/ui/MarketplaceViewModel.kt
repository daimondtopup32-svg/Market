package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MarketplaceViewModel(private val repository: MarketplaceRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    val products: StateFlow<List<Product>> = repository.availableProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    fun login(userId: String) {
        viewModelScope.launch {
            val user = repository.getUserById(userId)
            if (user == null) {
                // For demo purposes, create a mock user
                val newUser = User(
                    id = userId,
                    name = "Demo User",
                    email = "demo@example.com",
                    role = "BUYER"
                )
                repository.insertUser(newUser)
                _currentUser.value = newUser
            } else {
                _currentUser.value = user
            }
        }
    }

    fun selectProduct(productId: Int) {
        viewModelScope.launch {
            _selectedProduct.value = repository.getProductById(productId)
        }
    }

    fun purchaseProduct(product: Product) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val order = Order(
                productId = product.id,
                buyerId = user.id,
                sellerId = product.sellerId,
                amount = product.price,
                status = "PAID"
            )
            repository.insertOrder(order)
            // Update product status to PENDING
            repository.insertProduct(product.copy(status = "PENDING"))
        }
    }

    fun sendMessage(orderId: Int, content: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val message = Message(
                orderId = orderId,
                senderId = user.id,
                content = content
            )
            repository.insertMessage(message)
        }
    }

    fun seedData() {
        viewModelScope.launch {
            // Check if already seeded
            if (products.value.isEmpty()) {
                val demoSeller = User("seller_1", "Market Pro", "seller@market.com", "SELLER", 4.8)
                repository.insertUser(demoSeller)

                val demoProducts = listOf(
                    Product(
                        name = "SaaS Platform Template",
                        description = "Full-featured SaaS template with Auth and Stripe integration.",
                        category = "SaaS",
                        price = 299.0,
                        sellerId = "seller_1",
                        imageUrls = listOf("https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&q=80&w=2426")
                    ),
                    Product(
                        name = "E-commerce Mobile App",
                        description = "Native Android & iOS e-commerce solution using Kotlin Multiplatform.",
                        category = "Mobile App",
                        price = 450.0,
                        sellerId = "seller_1",
                        imageUrls = listOf("https://images.unsplash.com/photo-1512428559087-560fa5ceab42?auto=format&fit=crop&q=80&w=2400")
                    ),
                    Product(
                        name = "Domain: CloudFlow.io",
                        description = "Premium domain name for cloud-based automation tools.",
                        category = "Domain",
                        price = 1200.0,
                        sellerId = "seller_1",
                        imageUrls = listOf("https://images.unsplash.com/photo-1544197150-b99a580bb7a8?auto=format&fit=crop&q=80&w=2400")
                    ),
                    Product(
                        name = "AI Image Generator Source",
                        description = "Full source code for an AI image generation tool using Stable Diffusion.",
                        category = "Source Code",
                        price = 150.0,
                        sellerId = "seller_1",
                        imageUrls = listOf("https://images.unsplash.com/photo-1677442136019-21780ecad995?auto=format&fit=crop&q=80&w=2400")
                    )
                )
                demoProducts.forEach { repository.insertProduct(it) }
            }
        }
    }
}
