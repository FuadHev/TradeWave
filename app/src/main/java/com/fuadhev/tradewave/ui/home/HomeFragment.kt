package com.fuadhev.tradewave.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.alpha
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.goneEach
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visibleEach
import com.fuadhev.tradewave.databinding.FragmentHomeBinding
import com.fuadhev.tradewave.ui.home.adapter.CategoryAdapter
import com.fuadhev.tradewave.ui.home.adapter.OfferPagerAdapter
import com.fuadhev.tradewave.ui.home.adapter.PopularProductAdapter
import com.fuadhev.tradewave.ui.home.adapter.RecommendAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val offerAdapter = OfferPagerAdapter()
    private val categoryAdapter = CategoryAdapter()
    private val popularProductAdapter = PopularProductAdapter()
    private val recommendAdapter = RecommendAdapter()


    override fun observeEvents() {
        with(viewModel) {
            homeState.observe(viewLifecycleOwner) { handleState(it) }
        }
    }

    override fun onCreateFinish() {
        setAdapters()
    }

    override fun setupListeners() {
        with(binding){
//            ibFav.setOnClickListener {
//                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavouriteFragment())
//            }
//            tvCategory.setOnClickListener {
//                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExploreFragment())
//            }
//            offerAdapter.onClick = {
//                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOfferProductFragment(it))
//            }
            recommendAdapter.onClick={
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
            }
            popularProductAdapter.onClick={
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
            }
//            categoryAdapter.onClick = {
//                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductListFragment2(it))
//            }

        }

    }


    private fun handleState(it: HomeUiState) {
        with(binding) {
            when (it) {
                is HomeUiState.SuccessOfferData -> {
                    lyMain.alpha(1f)
                    offerAdapter.differ.submitList(it.list)
                    loading.gone()
                }

                is HomeUiState.SuccessCategoryData -> {
                    categoryAdapter.differ.submitList(it.list)
                    loading2.gone()
                }

                is HomeUiState.SuccessPopularData -> {
                    popularProductAdapter.differ.submitList(it.list)
                    loading3.gone()
                }

                is HomeUiState.SuccessRecommendData -> {
                    recommendAdapter.differ.submitList(it.list)
                    loading4.gone()
                }

                is HomeUiState.Error -> {
                    lyMain.alpha(1f)
                    listOf(loading,loading2,loading3,loading4).goneEach()
                    requireActivity().showMessage(it.message, FancyToast.ERROR)
                }

                is HomeUiState.Loading -> {
                    lyMain.alpha(0.6f)
                    listOf(loading,loading2,loading3,loading4).visibleEach()
                }
            }
        }

    }

    private fun setAdapters() {
        with(binding) {
            viewPager2.adapter = offerAdapter
            rvCategory.adapter = categoryAdapter
            rvPopular.adapter = popularProductAdapter
            rvRecommend.adapter = recommendAdapter
            springDotsIndicator.attachTo(viewPager2)
        }

    }



}