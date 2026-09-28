package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.UserRole

@Entity(tableName = "user_sessions")
data class UserSessionEntity(
    @PrimaryKey val id: Int = 1,
    val phoneNumber: String = "",
    val email: String = "",
    val role: String = UserRole.CUSTOMER.name,
    val isVerified: Boolean = false,
    val loginTimestamp: Long = System.currentTimeMillis()
)
