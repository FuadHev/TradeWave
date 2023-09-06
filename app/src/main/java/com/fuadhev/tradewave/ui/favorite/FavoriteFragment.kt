package com.fuadhev.tradewave.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentFavoriteBinding
import com.fuadhev.tradewave.ui.favorite.adapter.FavoriteAdapter
import com.shashank.sony.fancytoastlib.FancyToast

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel by viewModels<FavoriteViewModel> ()
    private val favoriteAdapter by lazy {
        FavoriteAdapter()
    }


    override fun observeEvents() {


    }

    override fun onCreateFinish() {

        setAdapter()
    }

    override fun setupListeners() {

    }

    private fun setAdapter(){
        binding.rvFav.adapter=favoriteAdapter
    }

    private fun handleState(state:FavoriteUiState){
        with(binding){
            when(state){
                is FavoriteUiState.Loading->{ loading5.visible()}
                is FavoriteUiState.SuccessFavData->{ loading5.gone()}
                is FavoriteUiState.SuccessDeleteData->{

                }
                is FavoriteUiState.Error->{
                    loading5.gone()
                    requireActivity().showMessage(state.message,FancyToast.ERROR)
                }

            }
        }

    }
}