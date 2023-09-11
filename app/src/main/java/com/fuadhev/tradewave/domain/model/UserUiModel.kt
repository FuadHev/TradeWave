package com.fuadhev.tradewave.domain.model

data class UserUiModel(
    var uid: String?=null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val phonenumber: String? = null,
    val gender: String? = null,
    val birthday: String? = null,
){
    fun fullName() = "$firstname $lastname"
}