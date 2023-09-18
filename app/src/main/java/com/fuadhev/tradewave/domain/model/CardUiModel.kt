package com.fuadhev.tradewave.domain.model

data class CardUiModel(
    val id:String,
    val cardNumber: String,
    val expirationDate: String,
    val securityCode: String,
    val cardHolder: String
) {
}