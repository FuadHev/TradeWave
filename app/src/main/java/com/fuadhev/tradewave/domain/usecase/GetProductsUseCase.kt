package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repo:ProductRepository){

    operator fun invoke()=repo.getProducts()
}