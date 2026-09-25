package com.example.data

import kotlinx.coroutines.flow.Flow

class MarketplaceRepository(private val dao: MarketplaceDao) {
    val availableProducts: Flow<List<Product>> = dao.getAvailableProducts()

    fun getProductsByCategory(category: String): Flow<List<Product>> = dao.getProductsByCategory(category)

    suspend fun getProductById(productId: Int) = dao.getProductById(productId)

    suspend fun insertProduct(product: Product) = dao.insertProduct(product)

    suspend fun getUserById(userId: String) = dao.getUserById(userId)

    suspend fun insertUser(user: User) = dao.insertUser(user)

    fun getOrdersForUser(userId: String): Flow<List<Order>> = dao.getOrdersForUser(userId)

    suspend fun insertOrder(order: Order) = dao.insertOrder(order)

    suspend fun updateOrder(order: Order) = dao.updateOrder(order)

    fun getMessagesForOrder(orderId: Int): Flow<List<Message>> = dao.getMessagesForOrder(orderId)

    suspend fun insertMessage(message: Message) = dao.insertMessage(message)
}
