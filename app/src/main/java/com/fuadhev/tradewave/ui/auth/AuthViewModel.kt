package com.fuadhev.tradewave.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import com.fuadhev.tradewave.domain.usecase.LoginUseCase
import com.fuadhev.tradewave.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val sp: SharedPrefManager
) : ViewModel() {

    private val _authState = MutableLiveData<AuthUiState>()
    val authState: LiveData<AuthUiState> get() = _authState


    fun loginUser(email: String, password: String) {
        viewModelScope.launch(IO) {

            loginUseCase.invoke(email, password).collectLatest {

                when (it) {
                    is Resource.Loading -> {
                        _authState.postValue(AuthUiState.Loading)
                    }

                    is Resource.Success -> {
                        sp.saveToken(it.data?.user?.uid)
                        _authState.postValue(AuthUiState.SuccessAuth)
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
            registerUseCase(email, password).collectLatest {
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




}