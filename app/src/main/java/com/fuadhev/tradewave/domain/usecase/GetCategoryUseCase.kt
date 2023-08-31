package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    operator fun invoke() = productRepository.getCategories()
}