package com.fuadhev.tradewave.domain.model

data class UserUiModel(
    var uid: String?=null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val gender: String? = null,
    val birthday: String? = null,
){
    fun fullName() = "$firstName $lastName"
}