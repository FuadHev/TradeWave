package com.fuadhev.tradewave.ui.offerList

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentOfferListBinding
import com.fuadhev.tradewave.ui.home.HomeUiState
import com.fuadhev.tradewave.ui.home.HomeViewModel
import com.fuadhev.tradewave.ui.home.adapter.OfferPagerAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferListFragment : BaseFragment<FragmentOfferListBinding>(FragmentOfferListBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val offerAdapter by lazy {
        OfferPagerAdapter()
    }

    override fun observeEvents() {
        viewModel.homeState.observe(viewLifecycleOwner){
            handleState(it)
        }

    }

    override fun onCreateFinish() {
        setAdapter()
    }

    override fun setupListeners() {
        offerAdapter.onClick={
            findNavController().navigate(OfferListFragmentDirections.actionOfferListFragmentToOfferProductFragment(it))
        }
    }

    private fun setAdapter(){
        with(binding){
            offerPager.adapter=offerAdapter
            dotsIndicator.attachTo(offerPager)
        }
    }

    private fun handleState(state:HomeUiState){
        with(binding){
            when(state){
                is HomeUiState.Loading->{ loadingView.visible()}
                is HomeUiState.SuccessOfferData->{
                    offerAdapter.differ.submitList(state.list)
                    loadingView.gone()
                }
                is HomeUiState.SuccessCategoryData->{}
                is HomeUiState.SuccessPopularData->{}
                is HomeUiState.SuccessRecommendData->{}
                is HomeUiState.Error->{

                    requireActivity().showMessage(state.message,FancyToast.ERROR)
                    loadingView.gone()
                }
            }
        }


    }


}