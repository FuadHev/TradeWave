package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.common.utils.InfoEnum
import com.fuadhev.tradewave.domain.model.UserUiModel
import com.fuadhev.tradewave.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val repo:AuthRepository) {

    fun loginUser(email:String,password:String)=repo.loginUser(email,password)
    fun addUser(userUiModel:UserUiModel)=repo.addUser(userUiModel)
    fun registerUser(email:String,password: String)=repo.registerUser(email,password)

    fun updateUser(info: InfoEnum, updatedData: String)=repo.updateUser(info,updatedData)
    fun getUserInfo()=repo.getUserInfo()
    fun logoutUser()=repo.logOutUser()
}