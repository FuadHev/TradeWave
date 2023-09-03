package com.fuadhev.tradewave.ui.explore

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.domain.mapper.Mapper.toProductUiList
import com.fuadhev.tradewave.domain.usecase.GetCategoryUseCase
import com.fuadhev.tradewave.domain.usecase.GetProductByCategoryUseCase
import com.fuadhev.tradewave.domain.usecase.GetProductUseCase
import com.fuadhev.tradewave.domain.usecase.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale.Category
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val getSearchUseCase: GetSearchUseCase
) :
    ViewModel() {

    private val _exploreState = MutableLiveData<ExploreUiState>()
    val exploreState: LiveData<ExploreUiState> get() = _exploreState

    init {
        getCategory()
    }

    private fun getCategory() {
        viewModelScope.launch {
            getCategoryUseCase().collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _exploreState.value = ExploreUiState.Loading
                    }

                    is Resource.Success -> {
                        _exploreState.value = it.data?.let { data ->
                            ExploreUiState.SuccessCategoryData(data)
                        }
                    }

                    is Resource.Error -> {
                        _exploreState.value = ExploreUiState.Error(it.exception)
                    }
                }
            }
        }
    }


    fun getSearch(query:String){
        viewModelScope.launch {
            getSearchUseCase(query).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _exploreState.value = ExploreUiState.Loading
                    }

                    is Resource.Success -> {
                        _exploreState.value = it.data?.let { data ->
                            ExploreUiState.SuccessSearchData(data.productDTOS.toProductUiList())
                        }
                    }

                    is Resource.Error -> {
                        _exploreState.value = ExploreUiState.Error(it.exception)
                    }
                }
            }
        }

    }

    fun getProductByCategory(category: String){
        viewModelScope.launch {
            getProductByCategoryUseCase(category).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _exploreState.value = ExploreUiState.Loading
                    }

                    is Resource.Success -> {
                        _exploreState.value = it.data?.let { data ->
                            ExploreUiState.SuccessProductData(data.productDTOS.toProductUiList())
                        }
                    }

                    is Resource.Error -> {
                        _exploreState.value = ExploreUiState.Error(it.exception)
                    }
                }
            }

        }

    }


}