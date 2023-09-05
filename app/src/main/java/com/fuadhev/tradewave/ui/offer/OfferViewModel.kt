package com.fuadhev.tradewave.ui.offer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.mapper.Mapper.toProductUiList
import com.fuadhev.tradewave.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :ViewModel() {

    private val _offerState=MutableLiveData<OfferUiState>()
    val offerState:LiveData<OfferUiState> get() = _offerState

    fun getProducts(discount:Double){
        viewModelScope.launch {
            getProductsUseCase().collectLatest {
                when(it){
                    is Resource.Loading->{
                        _offerState.postValue(OfferUiState.Loading)
                    }


                    is Resource.Success->{
                        _offerState.postValue(it.data?.let { it1 ->
                            OfferUiState.SuccessOfferProductData(
                                it1.productDTOS.toProductUiList().filter {
                                    it.discount.toInt()==discount.toInt()
                                }
                            )
                        })

                    }

                    is Resource.Error->{_offerState.postValue(OfferUiState.Error(it.exception)) }
                }
            }
        }
    }

}