package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.StoreConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreConfigDao {
    @Query("SELECT * FROM store_config WHERE id = 1 LIMIT 1")
    fun getConfigFlow(): Flow<StoreConfigEntity?>

    @Query("SELECT * FROM store_config WHERE id = 1 LIMIT 1")
    suspend fun getConfig(): StoreConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConfig(config: StoreConfigEntity)
}
