package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(private val repo:ProductRepository) {

    operator fun invoke(query:String)=repo.getSearch(query)
}