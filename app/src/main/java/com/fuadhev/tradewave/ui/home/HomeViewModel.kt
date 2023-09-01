package com.fuadhev.tradewave.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.mapper.Mapper.toProductUiList
import com.fuadhev.tradewave.domain.usecase.GetCategoryUseCase
import com.fuadhev.tradewave.domain.usecase.GetOfferUseCase
import com.fuadhev.tradewave.domain.usecase.GetProductByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getOfferUseCase: GetOfferUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
) : ViewModel() {

    private val _homeState = MutableLiveData<HomeUiState>()
    val homeState: LiveData<HomeUiState> get() = _homeState


    init {
        getCategories()
        getPopular()
        getOffers()
        getRecommend()
    }


    private fun getRecommend() {
        viewModelScope.launch {
            getProductByCategoryUseCase("laptops").collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value =
                            it.data?.let { it1 -> HomeUiState.SuccessRecommendData(it1.productDTOS.toProductUiList()) }
                    }

                    is Resource.Error -> {
                        _homeState.value = HomeUiState.Error(it.exception)
                    }

                    is Resource.Loading -> {
                        _homeState.value = HomeUiState.Loading
                    }

                }
            }
        }
    }


    private fun getPopular() {
        viewModelScope.launch {
            getProductByCategoryUseCase("tops").collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value =
                            it.data?.let { it1 -> HomeUiState.SuccessPopularData(it1.productDTOS.toProductUiList()) }
                    }

                    is Resource.Error -> {
                        _homeState.value = HomeUiState.Error(it.exception)
                    }

                    is Resource.Loading -> {
                        _homeState.value = HomeUiState.Loading
                    }

                }
            }
        }
    }


    private fun getOffers() {
        viewModelScope.launch {
            getOfferUseCase().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value =
                            it.data?.let { it1 -> HomeUiState.SuccessOfferData(it1.shuffled()) }
                    }

                    is Resource.Error -> {
                        _homeState.value = HomeUiState.Error(it.exception)
                    }

                    is Resource.Loading -> {
                        _homeState.value = HomeUiState.Loading
                    }

                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoryUseCase().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value = it.data?.let { it1 ->
                            HomeUiState.SuccessCategoryData(
                                it1.sortedBy { it.id })
                        }
                    }

                    is Resource.Error -> {
                        _homeState.value = HomeUiState.Error(it.exception)
                    }

                    is Resource.Loading -> {
                        _homeState.value = HomeUiState.Loading
                    }

                }
            }
        }
    }


}