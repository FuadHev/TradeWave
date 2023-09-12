package com.fuadhev.tradewave.data.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cart_product")
data class CartDTO (
    @PrimaryKey(false)
    val id : Int,
    val title : String,
    val price:Int,
    val image : String,
    val isFavorite : Boolean,
    val quantity : Int=1
)