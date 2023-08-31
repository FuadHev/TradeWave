package com.fuadhev.tradewave.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Singleton
//    @Provides
//    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDatabase =
//        Room.databaseBuilder(
//            context,
//            FavoriteDB::class.java,
//            "ProductDB"
//        )
//            .build()
//
//    @Singleton
//    @Provides
//    fun provideFavDao(db: FavoriteDB): FavoriteDAO = db.getFavDao()
}