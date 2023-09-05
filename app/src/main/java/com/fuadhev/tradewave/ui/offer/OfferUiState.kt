package com.fuadhev.tradewave.ui.offer

import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.ui.home.HomeUiState

sealed class OfferUiState {
    object Loading : OfferUiState()
    data class SuccessOfferProductData(val list : List<ProductUiModel>) : OfferUiState()
    data class Error(val message : String) : OfferUiState()
}