package com.fuadhev.tradewave.ui.detail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.alpha
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentDetailBinding
import com.fuadhev.tradewave.domain.model.ProductUiModel
import com.fuadhev.tradewave.ui.detail.adapter.ImageAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private val imageAdapter by lazy {
        ImageAdapter()
    }
    private lateinit var mProduct: ProductUiModel
    override fun observeEvents() {
        viewModel.detailState.observe(viewLifecycleOwner){
            handleState(it)
        }
    }

    override fun onCreateFinish() {
        setAdapter()
        viewModel.getProduct(args.id)

    }

    override fun setupListeners() {
    }

    private fun handleState(state: DetailUiState) {
        with(binding){

            when(state){
                is DetailUiState.Loading->{
                    lyMain.alpha(0.6f)
                    loadingView.visible()
                }

                is DetailUiState.SuccessProductData->{
                    lyMain.alpha(1f)
                    loadingView.gone()
                    setData(state.data)
                    Log.e("rating", state.data.rating.toString() )
                }
                is DetailUiState.SuccessFavData->{
                    lyMain.alpha(1f)
                    loadingView.gone()
                    state.message?.let {message->
                        requireActivity().showMessage(message,FancyToast.INFO)
                    }
                }
                is DetailUiState.Error->{
                    lyMain.alpha(1f)
                    loadingView.gone()
                }
            }
        }
    }

    private fun setAdapter(){
        with(binding){
            vpImage.adapter=imageAdapter
            springDotsIndicator.attachTo(vpImage)

        }
    }

    private fun setData(data:ProductUiModel){
        binding.product=data
        imageAdapter.differ.submitList(data.images)
        viewModel.isProductFavorit(data.id){
            binding.btnFav.isChecked=it
        }
    }

}