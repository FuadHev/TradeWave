package com.fuadhev.tradewave.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val sp:SharedPrefManager) :ViewModel() {

    private val _authData=MutableLiveData(false)
    val authData :LiveData<Boolean> get() = _authData

    init {
        getAuthData()
    }

    private fun getAuthData(){
        sp.getToken()?.let {
            _authData.postValue(true)
        }
    }


}