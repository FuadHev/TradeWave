package com.fuadhev.tradewave.ui.favorite

import com.fuadhev.tradewave.domain.model.FavoriteUiModel

sealed class FavoriteUiState {
    object Loading : FavoriteUiState()
    data class SuccessFavData(val data: List<FavoriteUiModel>) : FavoriteUiState()
    data class SuccessDeleteData(val message: String) : FavoriteUiState()
    data class Error(val message: String) : FavoriteUiState()
}