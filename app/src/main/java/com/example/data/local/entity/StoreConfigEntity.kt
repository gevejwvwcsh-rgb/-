package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store_config")
data class StoreConfigEntity(
    @PrimaryKey val id: Int = 1,
    val storePhone: String = "01020931398",
    val storeWhatsapp: String = "201020931398",
    val ownerName: String = "المعلم حماده رزق",
    val ownerPhoneLabel: String = "صاحب المحل المعلم حماده رزق رقم الفون 01020931398 مكلمات وتس",
    val developerName: String = "𝑯𝑨𝑴𝑶 𝑨𝑳𝑴𝑩𝑹𝑴𝑱",
    val developerPhone: String = "01096700804",
    val developerEmail: String = "gevejwvwcsh@gmail.com",
    val storeAddress: String = "محافظة اسيوط مركز ديروط قريه ساو انزل وسال قول عايز محل المعلم حماده رزق محل السباكه",
    val storeHours: String = "يوميًا 9:00 ص – 11:00 م (ما عدا الجمعة: من بعد الظهر حتى 11:00 م)",
    val updatedAt: Long = System.currentTimeMillis()
)
