package com.fuadhev.tradewave.domain.usecase

import com.fuadhev.tradewave.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo:AuthRepository) {

    operator fun invoke(email:String,password:String)=repo.loginUser(email,password)
}