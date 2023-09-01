package com.fuadhev.tradewave.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fuadhev.tradewave.data.local.dto.FavoriteDTO

@Database(entities = [FavoriteDTO::class], version = 1, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun getFavDao(): FavoriteDAO
}