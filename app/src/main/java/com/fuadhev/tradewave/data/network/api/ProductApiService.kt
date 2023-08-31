package com.fuadhev.tradewave.data.network.api

import com.fuadhev.tradewave.data.network.dto.ProductDTO
import com.fuadhev.tradewave.data.network.dto.ProductsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 100,
    ): ProductsDTO

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") slug: String,
        @Query("limit") limit: Int = 100
    ): ProductsDTO

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
    ): ProductDTO

    @GET("products/search")
    suspend fun getSearch(
        @Query("q") query: String,
    ): ProductsDTO


}