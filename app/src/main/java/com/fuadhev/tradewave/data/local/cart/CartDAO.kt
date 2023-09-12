package com.fuadhev.tradewave.data.local.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addCart(product: CartDTO)

    @Delete
     fun deleteCart(product: CartDTO)

    @Query("Select * from cart_product")
     fun getCart() : List<CartDTO>

}