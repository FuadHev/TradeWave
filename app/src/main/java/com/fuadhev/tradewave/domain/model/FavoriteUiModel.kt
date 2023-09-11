package com.fuadhev.tradewave.domain.model

data class FavoriteUiModel(
    val id : Int,
    val title : String,
    val rating:Int,
    val price:Int,
    val originalPrice : Double,
    val discount : Int,
    val image : String,
){
    fun formatPrice() = "$ $price"
    fun formatDiscount() = "% $discount Off "
}