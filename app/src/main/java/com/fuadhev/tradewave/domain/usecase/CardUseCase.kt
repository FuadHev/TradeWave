package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.domain.model.CardUiModel
import com.fuadhev.tradewave.domain.repository.ProductRepository
import javax.inject.Inject

class CardUseCase @Inject constructor(val repo:ProductRepository) {

    suspend fun getCards()=repo.getCards()

    fun addCard(card:CardUiModel)=repo.addCard(card)

    fun deleteCard(card:CardUiModel)=repo.deleteCard(card)
}