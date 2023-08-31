package com.fuadhev.tradewave.di

import com.fuadhev.tradewave.data.repository.AuthRepositoryImpl
import com.fuadhev.tradewave.data.repository.ProductRepositoryImpl
import com.fuadhev.tradewave.domain.repository.AuthRepository
import com.fuadhev.tradewave.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository


    @Singleton
    @Binds
    abstract fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}