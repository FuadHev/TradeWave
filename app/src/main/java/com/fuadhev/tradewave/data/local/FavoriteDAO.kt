package com.fuadhev.tradewave.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fuadhev.tradewave.data.local.dto.FavoriteDTO

@Dao
interface FavoriteDAO {
    @Insert
    fun addFav(product: FavoriteDTO)

    @Delete
    fun deleteFav(product: FavoriteDTO)

    @Query("SELECT * FROM favorite_product")
    fun getFav(): List<FavoriteDTO>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_product WHERE id = :productId)")
    suspend fun isProductFavorite(productId: Int): Boolean

}