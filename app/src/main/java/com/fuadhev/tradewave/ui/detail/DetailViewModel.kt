package com.fuadhev.tradewave.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.data.local.cart.CartDTO
import com.fuadhev.tradewave.data.network.dto.ProductDTO
import com.fuadhev.tradewave.domain.mapper.Mapper.toFavoriteDTO
import com.fuadhev.tradewave.domain.mapper.Mapper.toProductUiModel
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.domain.usecase.CartUseCase
import com.fuadhev.tradewave.domain.usecase.GetProductUseCase
import com.fuadhev.tradewave.domain.usecase.local.FavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val favUseCase: FavUseCase,
    private val cartUseCase: CartUseCase
) : ViewModel() {

    private val _detailState=MutableLiveData<DetailUiState>()
    val detailState:LiveData<DetailUiState> get() = _detailState



    fun getProduct(id:Int){
        viewModelScope.launch {
            getProductUseCase.invoke(id).collectLatest {
                when(it){
                    is Resource.Loading ->{ DetailUiState.Loading }
                    is Resource.Success ->{
                        _detailState.value= it.data?.let { data->
                            DetailUiState.SuccessProductData(data.toProductUiModel())
                        }
                    }
                    is Resource.Error ->{ DetailUiState.Error(it.exception)  }
                }
            }
        }
    }

    fun addFav(product:ProductUiModel){
        viewModelScope.launch {
            favUseCase.addFavorite(product.toFavoriteDTO()).collectLatest {
                when(it){

                    is Resource.Loading ->{ DetailUiState.Loading }
                    is Resource.Success ->{
                        DetailUiState.SuccessFavData("Added Succesfully")
                    }
                    is Resource.Error ->{ DetailUiState.Error(it.exception) }
                }
            }

        }

    }

    fun deleteFav(product: ProductUiModel){
        viewModelScope.launch {
            favUseCase.deleteFavorite(product.toFavoriteDTO()).collectLatest {
                    when(it){

                        is Resource.Loading ->{ DetailUiState.Loading }
                        is Resource.Success ->{
                            DetailUiState.SuccessFavData("Removed Succesfully")
                        }
                        is Resource.Error ->{ DetailUiState.Error(it.exception) }
                    }

            }
        }

    }

    fun isProductFavorit(productId:Int, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            favUseCase.isFavorite(productId).collectLatest {
                callback(it)
            }
        }
    }

    fun addCartProduct(product:CartDTO){
        viewModelScope.launch(IO) {
            cartUseCase.addProduct(product)
        }
    }



}