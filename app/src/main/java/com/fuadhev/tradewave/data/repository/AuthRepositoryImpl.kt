package com.fuadhev.tradewave.data.repository

import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import com.fuadhev.tradewave.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(val firebaseAuth:FirebaseAuth) :AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        val auth=firebaseAuth.signInWithEmailAndPassword(email,password).await()
        emit(Resource.Success(auth))

    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun getUserData(): Flow<Resource<FirebaseUser>> {
        TODO("Not yet implemented")
    }

    override fun logOutUser(): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }


}