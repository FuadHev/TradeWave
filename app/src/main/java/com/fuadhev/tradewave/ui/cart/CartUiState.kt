package com.fuadhev.tradewave.ui.cart

import com.fuadhev.tradewave.domain.model.CartUiModel
import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.ui.home.HomeUiState

sealed class CartUiState {

    object Loading : CartUiState()
    data class SuccessCartData(val list : List<CartUiModel>) : CartUiState()
    data class Error(val message : String) : CartUiState()
}