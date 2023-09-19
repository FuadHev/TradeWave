package com.fuadhev.tradewave.ui.profile.payment.addCard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.model.CardUiModel
import com.fuadhev.tradewave.domain.repository.ProductRepository
import com.fuadhev.tradewave.domain.usecase.CardUseCase
import com.fuadhev.tradewave.ui.profile.payment.CardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(val cardUseCase: CardUseCase) :ViewModel() {

    private val _cardState=MutableLiveData<CardUiState>()
    val cardState:LiveData<CardUiState> get() = _cardState

    fun addCard(card:CardUiModel){
        viewModelScope.launch {
            cardUseCase.addCard(card).collectLatest {
                when(it){
                    is  Resource.Loading ->{ _cardState.postValue(CardUiState.Loading) }
                    is  Resource.Success ->{_cardState.postValue(CardUiState.SuccessAddCard(it.data?:false))}
                    is  Resource.Error ->{_cardState.postValue(CardUiState.Error(it.exception))}
                }
            }
        }
    }

}