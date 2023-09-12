package com.fuadhev.tradewave.ui.cart

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.mapper.Mapper.toCartDTO
import com.fuadhev.tradewave.domain.mapper.Mapper.toCartUiList
import com.fuadhev.tradewave.domain.model.CartUiModel
import com.fuadhev.tradewave.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartUseCase: CartUseCase)  :ViewModel() {

    private val _cartState = MutableLiveData<CartUiState>()
    val cartState: LiveData<CartUiState> get() = _cartState

    private val _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice


    init {
        getProduct()
    }


    fun deleteProduct(product: CartUiModel) {
        viewModelScope.launch(IO) {
            cartUseCase.deleteProduct(product.toCartDTO())
            withContext(Main){
                _totalPrice.value = 0
            }
            getProduct()
        }
    }

    private fun getProduct(){
        viewModelScope.launch(IO){
            cartUseCase.getProduct().collectLatest {
                withContext(Main){
                    when(it){
                        is Resource.Error -> {_cartState.postValue(CartUiState.Error(it.exception))}
                        is Resource.Loading -> {_cartState.postValue(CartUiState.Loading)}
                        is Resource.Success -> {
                            _cartState.value = CartUiState.SuccessCartData(it.data?.toCartUiList() ?: emptyList())
                            setTotal(it.data?.toCartUiList() ?: emptyList())
                        }
                    }
                }

            }
        }
    }

    fun decreasePrice(price: Int) {
        _totalPrice.value = _totalPrice.value?.minus(price)
    }

    fun increasePrice(price: Int) {
        _totalPrice.value = _totalPrice.value?.plus(price)
    }

    private fun setTotal(list: List<CartUiModel>) {
        list.forEach { increasePrice(it.price) }
    }
}