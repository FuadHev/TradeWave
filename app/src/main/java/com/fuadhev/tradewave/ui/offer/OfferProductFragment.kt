package com.fuadhev.tradewave.ui.offer


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.setTimer
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentOfferProductBinding
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.ui.offer.adapter.ProductAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferProductFragment :
    BaseFragment<FragmentOfferProductBinding>(FragmentOfferProductBinding::inflate) {

    private val viewModel by viewModels<OfferViewModel>()
    private val productAdapter by lazy {
        ProductAdapter()
    }
    private val args by navArgs<OfferProductFragmentArgs>()

    override fun observeEvents() {
        viewModel.offerState.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    override fun onCreateFinish() {

        setAdapter()
        with(binding) {
            binding.offer = args.pOffer
            dynamicCountDownView.setTimer(args.pOffer.expirationDate, requireActivity())

        }

        viewModel.getProducts(args.pOffer.discount)

    }

    override fun setupListeners() {
        with(binding) {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }
            productAdapter.onClick = {
                findNavController().navigate(
                    OfferProductFragmentDirections.actionOfferProductFragmentToDetailFragment(
                        it
                    )
                )
            }
        }
    }

    private fun setAdapter() {
        binding.rvProduct.adapter = productAdapter
    }


    private fun handleState(state: OfferUiState) {

        with(binding) {
            when (state) {
                is OfferUiState.Loading -> {
                    loading.visible()
                }

                is OfferUiState.SuccessOfferProductData -> {

                    if (state.list.isEmpty()){
                        lyError.visible()
                        rvProduct.gone()
                    }else{
                        lyError.gone()
                        rvProduct.visible()
                    }
                    productAdapter.differ.submitList(state.list)
                    loading.gone()

                }
                is OfferUiState.Error -> {
                    loading.gone()
                    requireActivity().showMessage(state.message, FancyToast.ERROR)

                }
            }
        }


    }
}