package com.fuadhev.tradewave.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.showSnack
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentFavoriteBinding
import com.fuadhev.tradewave.ui.favorite.adapter.FavoriteAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel by viewModels<FavoriteViewModel> ()
    private val favoriteAdapter by lazy {
        FavoriteAdapter()
    }


    override fun observeEvents() {
        viewModel.favoriteUiState.observe(viewLifecycleOwner){
            handleState(it)
        }

    }

    override fun onCreateFinish() {

        setAdapter()

    }

    override fun setupListeners() {
        with(binding){
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }
            favoriteAdapter.onDelete={
                viewModel.deleteFav(it)
            }
            favoriteAdapter.onClick={
                findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it))
            }
        }
    }

    private fun setAdapter(){
        binding.rvFav.adapter=favoriteAdapter
    }

    private fun handleState(state:FavoriteUiState){
        with(binding){
            when(state){
                is FavoriteUiState.Loading->{ loading5.visible()}
                is FavoriteUiState.SuccessFavData->{
                    if (state.data.isEmpty()){tvEmpty.visible()}else{tvEmpty.gone()}
                    favoriteAdapter.differ.submitList(state.data)
                    loading5.gone()

                }
                is FavoriteUiState.SuccessDeleteData->{
                    requireView().showSnack(state.message)

                }
                is FavoriteUiState.Error->{
                    loading5.gone()
                    requireActivity().showMessage(state.message,FancyToast.ERROR)
                }

            }
        }

    }
}