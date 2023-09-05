package com.fuadhev.tradewave.ui.explore.productList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentProductListBinding
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.ui.explore.ExploreUiState
import com.fuadhev.tradewave.ui.explore.ExploreViewModel
import com.fuadhev.tradewave.ui.offer.adapter.ProductAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductListFragment :BaseFragment<FragmentProductListBinding>(FragmentProductListBinding::inflate) {

    private val args by navArgs<ProductListFragmentArgs>()
    private val viewModel :ExploreViewModel by viewModels()
    private val productAdapter by lazy {
        ProductAdapter()
    }
    private var mCategory =""

    override fun observeEvents() {
        viewModel.exploreState.observe(viewLifecycleOwner){
            handleState(it)
        }
    }

    override fun onCreateFinish() {
        Log.e("arg", args.category)
        mCategory=args.category
        setAdapter()
        initSpinner()
        binding.category=mCategory
        viewModel.getProductByCategory(mCategory)

    }

    private fun setAdapter(){
        binding.rvProduct.adapter=productAdapter
    }

    private fun initSpinner() {
        val list =  resources.getStringArray(R.array.product_categories).toList()
        binding.spinner2.item =list
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                mCategory = list[position].toString()
                viewModel.getProductByCategory(mCategory)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

    }

    override fun setupListeners() {
        with(binding){
            ibBack.setOnClickListener{
//                findNavController().popBackStack()
                findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToExploreFragment())
            }
            productAdapter.onClick={
                findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToDetailFragment(it))
            }
        }

    }


    private fun handleState(state:ExploreUiState){
        with(binding){
            when(state){

                is ExploreUiState.SuccessCategoryData->{}
                is ExploreUiState.SuccessSearchData->{}
                is ExploreUiState.SuccessProductData->{
                    setProductData(state.data)
                    loadingView2.gone()
                }
                is ExploreUiState.Error->{
                    loadingView2.gone()
                    requireActivity().showMessage(state.message, FancyToast.ERROR)
                }
                is ExploreUiState.Loading->{
                    loadingView2.visible()
                }
            }

        }

    }
    private fun setProductData(data: List<ProductUiModel>) {
        with(binding){
            category = mCategory
            productAdapter.differ.submitList(data)
            tvResult2.text = "${data.size} Result"
        }
    }

}