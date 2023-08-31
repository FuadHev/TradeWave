package com.fuadhev.tradewave.di

import com.fuadhev.tradewave.common.utils.Constants.BASE_URL
import com.fuadhev.tradewave.data.network.api.ProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()).build()


    @Singleton
    @Provides
    fun provideApiService(retrofit:Retrofit) = retrofit.create(ProductApiService::class.java)

}