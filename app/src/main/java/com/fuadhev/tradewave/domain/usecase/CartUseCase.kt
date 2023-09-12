package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.data.local.cart.CartDTO
import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(val repo:ProductRepository) {

    fun addProduct(product:CartDTO)=repo.addCart(product)
    fun deleteProduct(product:CartDTO)=repo.deleteCart(product)
    fun getProduct()=repo.getCart()
}