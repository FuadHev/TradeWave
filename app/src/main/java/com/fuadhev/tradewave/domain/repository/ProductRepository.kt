package com.fuadhev.tradewave.domain.repository

import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.data.network.dto.ProductDTO
import com.fuadhev.tradewave.data.network.dto.ProductsDTO
import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getOffers(): Flow<Resource<List<OfferUiModel>>>
    fun getCategories(): Flow<Resource<List<CategoryUiModel>>>
    fun getProductByCategory(category:String) : Flow<Resource<ProductsDTO>>
    fun getProducts() : Flow<Resource<ProductsDTO>>
    fun getProduct(id:Int) : Flow<Resource<ProductDTO>>
    fun getSearch(query:String) : Flow<Resource<ProductsDTO>>
}