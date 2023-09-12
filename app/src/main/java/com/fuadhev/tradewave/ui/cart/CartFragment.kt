package com.fuadhev.tradewave.ui.cart


import androidx.fragment.app.viewModels
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentCartBinding
import com.fuadhev.tradewave.ui.cart.adapter.CartAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    private val viewModel by viewModels<CartViewModel>()
    private val cartAdapter by lazy {
        CartAdapter()
    }

    override fun observeEvents() {
        viewModel.cartState.observe(viewLifecycleOwner) { handleState(it) }
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.total = it.toDouble()
        }
    }

    fun handleState(state: CartUiState) {
        with(binding) {
            when (state) {
                is CartUiState.Loading -> {
                    loadingView.visible()
                }

                is CartUiState.SuccessCartData -> {
                    loadingView.gone()
                    if (state.list.isEmpty())tvEmpty.visible() else tvEmpty.gone()
                    cartAdapter.differ.submitList(state.list)
                    binding.size=state.list.size

                }

                is CartUiState.Error -> {
                    loadingView.gone()

                    requireActivity().showMessage(state.message, FancyToast.ERROR)
                }
            }
        }

    }


    override fun onCreateFinish() {
        binding.rvCart.adapter = cartAdapter
    }

    override fun setupListeners() {

        with(binding) {
            cartAdapter.onDelete = {
                viewModel.deleteProduct(it)
            }
            cartAdapter.onClickMinus = {
                viewModel.decreasePrice(it)
            }
            cartAdapter.onClickPlus = {
                viewModel.increasePrice(it)
            }
        }

    }

}