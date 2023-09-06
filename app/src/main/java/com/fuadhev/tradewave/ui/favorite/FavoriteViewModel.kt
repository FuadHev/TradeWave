package com.fuadhev.tradewave.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.mapper.Mapper.toFavUiModelList
import com.fuadhev.tradewave.domain.mapper.Mapper.toFavoriteDTO
import com.fuadhev.tradewave.domain.model.FavoriteUiModel
import com.fuadhev.tradewave.domain.usecase.local.FavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val getFavUseCase: FavUseCase):ViewModel() {

    private val _favoriteState=MutableLiveData<FavoriteUiState>()
    val favoriteUiState:LiveData<FavoriteUiState> get() = _favoriteState

    init {
        getFav()
    }
    fun getFav(){
        viewModelScope.launch {
            getFavUseCase.getFavorites().collectLatest {
                when(it){
                    is Resource.Loading->{ _favoriteState.value=FavoriteUiState.Loading}
                    is Resource.Success-> {
                        _favoriteState.value= it.data?.let {it1->
                            FavoriteUiState.SuccessFavData(it1.toFavUiModelList())
                        }
                    }
                    is Resource.Error->{_favoriteState.value=FavoriteUiState.Error(it.exception)}
                }
            }
        }
    }

    fun deleteFav(product:FavoriteUiModel){
        viewModelScope.launch {
            getFavUseCase.deleteFavorite(product.toFavoriteDTO()).collectLatest {
                when(it){
                    is Resource.Loading->{ _favoriteState.value=FavoriteUiState.Loading}
                    is Resource.Success-> {
                        _favoriteState.value=FavoriteUiState.SuccessDeleteData("Deleted")
                        getFav()
                    }
                    is Resource.Error->{_favoriteState.value=FavoriteUiState.Error(it.exception)}
                }
            }
        }
    }

}