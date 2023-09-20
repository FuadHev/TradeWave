package com.fuadhev.tradewave.ui.profile.payment.creditcard

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
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentCreditCardBinding
import com.fuadhev.tradewave.ui.profile.payment.CardUiState
import com.fuadhev.tradewave.ui.profile.payment.adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCardFragment : BaseFragment<FragmentCreditCardBinding>(FragmentCreditCardBinding::inflate) {


    private val viewModel by viewModels<CreditCardViewModel>()
    private val cardAdapter by lazy {
        CardAdapter()
    }

    override fun observeEvents() {
        viewModel.cardState.observe(viewLifecycleOwner){
            handleState(it)
        }
    }

    private fun handleState(state:CardUiState){
        with(binding){

            when(state){
                is CardUiState.Loading->{  }
                is CardUiState.SuccessCardData->{
                    if (state.list.isEmpty()){ noCard.visible()}else{noCard.gone()}
                    cardAdapter.differ.submitList(state.list)
                }
                is CardUiState.Error->{  }

                else->{}
            }
        }

    }

    override fun onCreateFinish() {
        binding.rvCard.adapter=cardAdapter
    }

    override fun setupListeners() {

        with(binding){
            btnAddCart.setOnClickListener {
                findNavController().navigate(CreditCardFragmentDirections.actionCreditCardFragmentToAddCardFragment())
            }
        }

    }

}