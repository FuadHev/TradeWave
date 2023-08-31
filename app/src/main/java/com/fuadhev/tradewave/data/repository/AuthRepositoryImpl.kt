package com.fuadhev.tradewave.data.repository

import android.util.Log
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

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth:FirebaseAuth) :AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        Log.e("email","$email $password" )
        emit(Resource.Loading)
        val auth=firebaseAuth.signInWithEmailAndPassword(email,password).await()
        Log.e("TAG", auth.user!!.uid.toString() )
        emit(Resource.Success(auth))

    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        val auth=firebaseAuth.createUserWithEmailAndPassword(email,password).await()
        emit(Resource.Success(auth))

    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }

    override fun getUserData(): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        val user = firebaseAuth.currentUser
        emit(Resource.Success(user))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun logOutUser(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        firebaseAuth.signOut()
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }


}