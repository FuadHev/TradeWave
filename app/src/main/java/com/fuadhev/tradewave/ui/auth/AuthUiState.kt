package com.fuadhev.tradewave.ui.auth

sealed class AuthUiState {
    object Loading : AuthUiState()
    object SuccessAuth : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}