package com.fuadhev.tradewave.ui.detail

import com.fuadhev.tradewave.domain.model.ProductUiModel

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class SuccessProductData(val data: ProductUiModel) : DetailUiState()
    data class SuccessFavData(val message: String?=null) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}