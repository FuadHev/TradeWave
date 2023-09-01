package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repo:ProductRepository) {

    operator fun invoke(id:Int)=repo.getProduct(id)
}