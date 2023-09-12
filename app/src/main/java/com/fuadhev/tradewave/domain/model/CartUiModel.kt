package com.fuadhev.tradewave.domain.model

data class CartUiModel(
    val id : Int,
    val title : String,
    var price:Int,
    val image : String,
    val isFavorite : Boolean,
    var quantity : Int
)