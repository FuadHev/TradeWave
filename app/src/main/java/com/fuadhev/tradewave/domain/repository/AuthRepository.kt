package com.fuadhev.tradewave.domain.repository

import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.model.UserUiModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun getUserData(): Flow<Resource<FirebaseUser>>
    fun logOutUser(): Flow<Resource<Boolean>>
    fun addUser(userUiModel: UserUiModel): Flow<Resource<Boolean>>

}