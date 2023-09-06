package com.fuadhev.tradewave.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.domain.usecase.local.FavUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val getFavUseCase: FavUseCase):ViewModel() {

    private val _favoriteState=MutableLiveData<FavoriteUiState>()
    val favoriteUiState:LiveData<FavoriteUiState> get() = _favoriteState

    fun getFav(){
        viewModelScope.launch {

        }
    }

}