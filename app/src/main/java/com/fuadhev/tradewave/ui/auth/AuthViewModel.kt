package com.fuadhev.tradewave.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import com.fuadhev.tradewave.domain.model.UserUiModel
import com.fuadhev.tradewave.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sp: SharedPrefManager
) : ViewModel() {

    private val _authState = MutableLiveData<AuthUiState>()
    val authState: LiveData<AuthUiState> get() = _authState


    fun loginUser(email: String, password: String,userUiModel: UserUiModel) {
        viewModelScope.launch(IO) {

            authUseCase.loginUser(email, password).collectLatest {

                when (it) {
                    is Resource.Loading -> {
                        _authState.postValue(AuthUiState.Loading)
                    }

                    is Resource.Success -> {
                        sp.saveToken(it.data?.user?.uid)
                        _authState.postValue(AuthUiState.SuccessAuth)
                        addUser(userUiModel)
                    }

                    is Resource.Error -> {
                        _authState.postValue(AuthUiState.Error(it.exception))
                    }


                }

            }

        }
    }
    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.registerUser(email, password).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _authState.postValue(AuthUiState.Loading)
                    }

                    is Resource.Success -> {
                        _authState.postValue(AuthUiState.SuccessAuth)
                    }

                    is Resource.Error -> {
                        _authState.postValue(AuthUiState.Error(it.exception))
                    }
                }
            }
        }
    }

    private fun addUser(userUiModel: UserUiModel) {
        viewModelScope.launch {
            authUseCase.addUser(userUiModel).collectLatest {
                when(it){
                    is Resource.Loading -> {
                        _authState.postValue(AuthUiState.Loading)
                    }

                    is Resource.Success -> {
                        _authState.postValue(AuthUiState.SuccessAuth)
                    }

                    is Resource.Error -> {
                        _authState.postValue(AuthUiState.Error(it.exception))
                    }
                }
            }
        }
    }
    fun logoutUser() {
        viewModelScope.launch {
            authUseCase.logoutUser().collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _authState.postValue(AuthUiState.Loading)
                    }

                    is Resource.Success -> {
                        sp.saveToken("")
                        _authState.postValue(AuthUiState.SuccessAuth)
                    }

                    is Resource.Error -> {
                        _authState.postValue(AuthUiState.Error(it.exception))
                    }
                }
            }
        }
    }



}