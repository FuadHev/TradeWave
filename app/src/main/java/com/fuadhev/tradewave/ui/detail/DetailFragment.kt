package com.fuadhev.tradewave.ui.detail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Constants
import com.fuadhev.tradewave.common.utils.Constants.TOKEN_USER
import com.fuadhev.tradewave.common.utils.Extensions.alpha
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.showSnack
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentDetailBinding
import com.fuadhev.tradewave.domain.mapper.Mapper.toCartDTO
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
        viewModel.detailState.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }



    override fun onCreateFinish() {
        setAdapter()
        viewModel.getProduct(args.id)

    }

    override fun setupListeners() {
        with(binding) {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnFav.setOnCheckedChangeListener { _, b ->
                if (b) {
                    product?.id?.let { it ->
                        viewModel.isProductFavorit(it) {
                            if (!it) {
                                viewModel.addFav(mProduct)
                            }
                        }
                    }
                } else {
                    viewModel.deleteFav(mProduct)
                }
            }

            btnAddCart.setOnClickListener {
                viewModel.isProductFavorit(args.id) {
                    viewModel.addCartProduct(
                        mProduct.toCartDTO(it)
                    )
                }
                requireView().showSnack("Added to cart")
            }



        }
    }

    private fun handleState(state: DetailUiState) {
        with(binding) {

            when (state) {
                is DetailUiState.Loading -> {
                    lyMain.alpha(0.6f)
                    loadingView.visible()
                }

                is DetailUiState.SuccessProductData -> {
                    lyMain.alpha(1f)
                    loadingView.gone()
                    setData(state.data)

                }

                is DetailUiState.SuccessFavData -> {
                    lyMain.alpha(1f)
                    loadingView.gone()
                    state.message?.let { message ->
                        requireActivity().showMessage(message, FancyToast.INFO)
                    }
                }

                is DetailUiState.Error -> {
                    lyMain.alpha(1f)
                    loadingView.gone()
                }

                else -> {}
            }
        }
    }

    private fun setAdapter() {
        with(binding) {
            vpImage.adapter = imageAdapter
            springDotsIndicator.attachTo(vpImage)

        }
    }

    private fun setData(data: ProductUiModel) {
        mProduct = data
        binding.product = data
        imageAdapter.differ.submitList(data.images)
        viewModel.isProductFavorit(data.id) {
            Log.e("isfav", it.toString())
            binding.btnFav.isChecked = it
        }
    }

}