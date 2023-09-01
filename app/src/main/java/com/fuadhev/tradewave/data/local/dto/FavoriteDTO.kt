package com.fuadhev.tradewave.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_product")
data class FavoriteDTO(
    @PrimaryKey(false)
    val id : Int,
    val title : String,
    val rating:Int,
    val price:Int,
    val originalPrice : Double,
    val discount : Int,
    val image : String,
)