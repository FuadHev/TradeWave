package com.fuadhev.tradewave.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentExploreBinding
import com.fuadhev.tradewave.ui.home.adapter.CategoryAdapter
import com.fuadhev.tradewave.ui.offer.adapter.ProductAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {
    private val viewModel by viewModels<ExploreViewModel>()
    private val categoryAdapter by lazy {
        CategoryAdapter()
    }
    private val productAdapter by lazy {
        ProductAdapter()
    }

    override fun observeEvents() {
        viewModel.exploreState.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    override fun onCreateFinish() {
        setAdapter()
    }


    override fun setupListeners() {

        with(binding) {
            categoryAdapter.onClick = {
                findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToProductListFragment(it))
            }
            btnBack.setOnClickListener {
                findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToHomeFragment())
            }
            productAdapter.onClick={
                findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToDetailFragment(it))
            }
            ibFilter.setOnClickListener {
                findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToFilterFragment())
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.getSearch(query) }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query.isNullOrEmpty()) {
                        lySearch.gone()
                        lyError.gone()
                        lyCategory.visible()
                    } else {
                        lySearch.visible()
                        lyCategory.gone()
                    }
                    return false
                }
            })
        }

    }
    private fun setAdapter() {
        with(binding) {
            rvCategory.adapter = categoryAdapter
            rvSearch.adapter = productAdapter
        }
    }


    private fun handleState(state: ExploreUiState) {
        with(binding) {
            when (state) {
                is ExploreUiState.Loading -> {
                    loadingView.visible()
                }
                is ExploreUiState.SuccessCategoryData -> {
                    categoryAdapter.differ.submitList(state.data)
                    loadingView.gone()
                }

                is ExploreUiState.SuccessProductData -> {
                    loadingView.gone()
                }

                is ExploreUiState.SuccessSearchData -> {
                    if (state.data.isEmpty()) { lyError.visible() } else { lyError.gone() }
                    tvResult.text="${state.data.size} Result"
                    productAdapter.differ.submitList(state.data)
                    loadingView.gone()
                }

                is ExploreUiState.Error-> {
                    loadingView.gone()
                    requireActivity().showMessage(state.message, FancyToast.ERROR) }
            }
        }

    }

}