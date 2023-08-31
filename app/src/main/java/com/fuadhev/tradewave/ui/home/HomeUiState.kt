package com.fuadhev.tradewave.ui.home

import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel

sealed class HomeUiState{
    object Loading :HomeUiState()
    data class SuccessOfferData(val list : List<OfferUiModel>) : HomeUiState()
    data class SuccessCategoryData(val list : List<CategoryUiModel>) : HomeUiState()
    data class SuccessPopularData(val list : List<ProductUiModel>) : HomeUiState()
    data class SuccessRecommendData(val list : List<ProductUiModel>) : HomeUiState()
    data class Error(val message : String) : HomeUiState()
}