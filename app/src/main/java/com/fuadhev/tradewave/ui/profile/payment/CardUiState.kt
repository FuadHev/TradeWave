package com.fuadhev.tradewave.ui.profile.payment

import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.ui.home.HomeUiState

sealed class CardUiState {

    object Loading : CardUiState()
    data class SuccessCardData(val list : List<OfferUiModel>) : CardUiState()
    data class SuccessAddCard(val success: Boolean) : CardUiState()
    data class Error(val message : String) : CardUiState()
}