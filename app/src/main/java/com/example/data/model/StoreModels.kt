package com.example.data.model

import androidx.annotation.DrawableRes

data class Category(
    val id: String,
    val nameAr: String,
    val subtitleAr: String,
    val iconName: String,
    val itemCount: Int
)

data class ProductSpec(
    val labelAr: String,
    val valueAr: String
)

data class Product(
    val id: String,
    val categoryId: String,
    val nameAr: String,
    val brand: String,
    val price: Double,
    val wholesalePrice: Double,
    val unitAr: String,
    val descriptionAr: String,
    @DrawableRes val imageRes: Int,
    @DrawableRes val galleryImages: List<Int> = emptyList(),
    val specs: List<ProductSpec>,
    val isFeatured: Boolean = false,
    val inStock: Boolean = true,
    val warrantyYears: Int = 0,
    val discountPercent: Int = 0,
    val tagAr: String = ""
)

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val totalPrice: Double get() = product.price * quantity
    val totalWholesalePrice: Double get() = product.wholesalePrice * quantity
}

data class CustomerReview(
    val id: String,
    val customerName: String,
    val roleOrLocation: String,
    val rating: Int,
    val comment: String,
    val dateAr: String
)

data class ShowroomItem(
    val id: String,
    val titleAr: String,
    val descriptionAr: String,
    @DrawableRes val imageRes: Int
)

data class StoreFeatureItem(
    val id: String,
    val titleAr: String,
    val descriptionAr: String,
    val iconType: String
)

enum class UserRole {
    CUSTOMER,
    DEVELOPER
}

data class UserSession(
    val isLoggedIn: Boolean = false,
    val phoneNumber: String = "",
    val email: String = "",
    val role: UserRole = UserRole.CUSTOMER,
    val isVerified: Boolean = false
) {
    val isDeveloper: Boolean get() = role == UserRole.DEVELOPER
}
