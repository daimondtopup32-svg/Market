package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val role: String, // "BUYER", "SELLER", "ADMIN"
    val rating: Double = 0.0,
    val profileImageUrl: String? = null,
    val isVerified: Boolean = false
)

@Serializable
@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val sellerId: String,
    val imageUrls: List<String>,
    val status: String = "AVAILABLE", // "AVAILABLE", "SOLD", "PENDING"
    val createdAt: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val buyerId: String,
    val sellerId: String,
    val amount: Double,
    val status: String = "PENDING", // "PENDING", "PAID", "RELEASED", "DISPUTED"
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val senderId: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null
)
