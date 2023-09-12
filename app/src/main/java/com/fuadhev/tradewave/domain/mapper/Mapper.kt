package com.fuadhev.tradewave.domain.mapper

import com.fuadhev.tradewave.data.local.cart.CartDTO
import com.fuadhev.tradewave.data.local.dto.FavoriteDTO
import com.fuadhev.tradewave.data.network.dto.ProductDTO
import com.fuadhev.tradewave.domain.model.CartUiModel
import com.fuadhev.tradewave.domain.model.FavoriteUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel


object Mapper {

    fun ProductDTO.toProductUiModel() =
        ProductUiModel(
            id,
            title.capitalize(),
            description,
            rating.toInt(),
            discountPercentage,
            images,
            price,
            price / (1 - (discountPercentage / 100)),
            stock,
            category,
            brand,
            thumbnail

        )


    fun ProductUiModel.toFavoriteDTO() =
        FavoriteDTO(
            id,
            title,
            rating,
            price,
            originalPrice,
            discount.toInt(),
            images[0]
        )


    fun CartUiModel.toCartDTO() =
        CartDTO(
            id,
            title,
            price,
            image,
            isFavorite,
            quantity
        )
    fun ProductUiModel.toCartDTO(favorite:Boolean) =
        CartDTO(
            id,
            title,
            price,
            images[0],
            favorite,
            1
        )
    fun List<CartDTO>.toCartUiList() = map {
        CartUiModel(
            it.id,
            it.title,
            it.price,
            it.image,
            it.isFavorite,
            it.quantity
        )
    }
    fun FavoriteUiModel.toFavoriteDTO() =
        FavoriteDTO(
            id,
            title,
            rating,
            price,
            originalPrice,
            discount,
            image
        )

    fun List<FavoriteDTO>.toFavUiModelList() = map {
        FavoriteUiModel(
            it.id,
            it.title,
            it.rating,
            it.price,
            it.originalPrice,
            it.discount,
            it.image
        )
    }



    fun List<ProductDTO>.toProductUiList() = map {
        ProductUiModel(
            it.id,
            it.title.capitalize(),
            it.description,
            it.rating.toInt(),
            it.discountPercentage,
            it.images,
            it.price,
            it.price / (1 - (it.discountPercentage / 100)),
            it.stock,
            it.category,
            it.brand,
            it.thumbnail
        )
    }
}