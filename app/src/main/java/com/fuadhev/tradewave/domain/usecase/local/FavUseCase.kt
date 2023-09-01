package com.fuadhev.tradewave.domain.usecase.local

import com.fuadhev.tradewave.data.local.dto.FavoriteDTO
import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class FavUseCase @Inject constructor(private val repo:ProductRepository) {

    fun addFavorite(product: FavoriteDTO) = repo.addFav(product)
    fun deleteFavorite(product: FavoriteDTO) = repo.deleteFav(product)
    fun getFavorites() = repo.getFav()
    fun isFavorite(productId: Int) = repo.isProductFavorite(productId)
}