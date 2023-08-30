package com.fuadhev.tradewave.di

import android.content.Context
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import com.fuadhev.tradewave.common.utils.ValidationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedManager(@ApplicationContext context: Context) = SharedPrefManager(context)

    @Singleton
    @Provides
    fun provideValidationHelper(@ApplicationContext context: Context) = ValidationHelper(context)



}