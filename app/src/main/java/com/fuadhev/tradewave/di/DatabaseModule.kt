package com.fuadhev.tradewave.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fuadhev.tradewave.data.local.FavoriteDAO
import com.fuadhev.tradewave.data.local.FavoriteDB
import com.fuadhev.tradewave.data.local.cart.CartDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): FavoriteDB =
        Room.databaseBuilder(
            context,
            FavoriteDB::class.java,
            "ProductDB"
        ).build()

    @Singleton
    @Provides
    fun provideFavDao(db: FavoriteDB): FavoriteDAO = db.getFavDao()

    @Singleton
    @Provides
    fun provideCartDao(db: FavoriteDB): CartDAO = db.getCartDao()
}