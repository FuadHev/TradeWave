package com.fuadhev.tradewave.ui.profile.payment

import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.databinding.FragmentAddPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPaymentFragment : BaseFragment<FragmentAddPaymentBinding>(FragmentAddPaymentBinding::inflate) {


    override fun observeEvents() {

    }

    override fun onCreateFinish() {

    }

    override fun setupListeners() {

        with(binding){
            btnCredit.setOnClickListener {
                findNavController().navigate(com.fuadhev.tradewave.ui.profile.payment.AddPaymentFragmentDirections.actionAddPaymentFragmentToCreditCardFragment())
            }

        }

    }

}