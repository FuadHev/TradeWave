package com.fuadhev.tradewave.ui.profile.payment.creditcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.usecase.CardUseCase
import com.fuadhev.tradewave.ui.profile.payment.CardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardViewModel @Inject constructor(val cardUseCase: CardUseCase) :ViewModel() {


    private val _cardState= MutableLiveData<CardUiState>()
    val cardState: LiveData<CardUiState> get() = _cardState

    init {
        getCards()
    }
    private fun getCards(){
        viewModelScope.launch {
            cardUseCase.getCards().collectLatest {
                when(it){
                    is  Resource.Loading ->{ _cardState.postValue(CardUiState.Loading) }
                    is  Resource.Success ->{_cardState.postValue(CardUiState.SuccessCardData(it.data?: emptyList()))}
                    is  Resource.Error ->{_cardState.postValue(CardUiState.Error(it.exception))}
                }
            }
        }
    }
}