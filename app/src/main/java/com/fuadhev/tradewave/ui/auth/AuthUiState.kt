package com.fuadhev.tradewave.ui.auth

import com.fuadhev.tradewave.domain.model.UserUiModel

sealed class AuthUiState {
    object Loading : AuthUiState()
    object SuccessAuth : AuthUiState()
    data class SuccessUserInfo(val data: UserUiModel) : AuthUiState()
    data class SuccessUpdateInfo(val success: Boolean) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}