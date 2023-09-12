package com.fuadhev.tradewave.domain.repository

import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.data.local.cart.CartDTO
import com.fuadhev.tradewave.data.local.dto.FavoriteDTO
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
    fun addFav(product:FavoriteDTO) : Flow<Resource<Boolean>>
    fun deleteFav(product:FavoriteDTO) : Flow<Resource<Boolean>>
    fun getFav() : Flow<Resource<List<FavoriteDTO>>>
    fun isProductFavorite(id:Int) : Flow<Boolean>

     fun addCart(product: CartDTO)
     fun deleteCart(product: CartDTO)
     fun getCart() : Flow<Resource<List<CartDTO>>>
}