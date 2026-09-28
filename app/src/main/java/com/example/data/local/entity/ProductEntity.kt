package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val nameAr: String,
    val brand: String,
    val price: Double,
    val wholesalePrice: Double,
    val unitAr: String,
    val descriptionAr: String,
    val imageResName: String = "img_pipes_hero",
    val isFeatured: Boolean = false,
    val isAvailable: Boolean = true,
    val isCustom: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
