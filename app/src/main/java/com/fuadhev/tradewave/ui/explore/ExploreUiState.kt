package com.fuadhev.tradewave.ui.explore

import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.ui.detail.DetailUiState

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class SuccessCategoryData(val data: List<CategoryUiModel>) : ExploreUiState()
    data class SuccessSearchData(val data: List<ProductUiModel>) : ExploreUiState()
    data class SuccessProductData(val data: List<ProductUiModel>) : ExploreUiState()
    data class Error(val message: String) : ExploreUiState()

}