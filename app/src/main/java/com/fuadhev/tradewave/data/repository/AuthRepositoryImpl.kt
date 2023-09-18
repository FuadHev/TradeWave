package com.fuadhev.tradewave.data.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.fuadhev.tradewave.common.utils.InfoEnum
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import com.fuadhev.tradewave.domain.model.UserUiModel
import com.fuadhev.tradewave.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth:FirebaseAuth,
    private val firestore:FirebaseFirestore) :AuthRepository {
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

    override fun getUserInfo(): Flow<Resource<UserUiModel>> = flow {
        emit(Resource.Loading)

        val uid = firebaseAuth.currentUser?.uid

        val userDocument = FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid ?: "")
            .get()
            .await()

        val userUiModel = userDocument.toObject(UserUiModel::class.java)
        emit(Resource.Success(userUiModel))

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

    override fun addUser(userUiModel: UserUiModel): Flow<Resource<Boolean>> =flow{
        emit(Resource.Loading)
        val uid=firebaseAuth.currentUser?.uid ?: ""
        userUiModel.uid=uid
        firestore.collection("users").document(uid).set(userUiModel).await()
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }

    override fun updateUser(info: InfoEnum, updatedData: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        val uid = firebaseAuth.currentUser?.uid ?: ""
        val updateData = mapOf(info.toString().lowercase(Locale.ROOT) to updatedData)

        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.update(updateData).await()
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }


}