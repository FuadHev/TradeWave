package com.fuadhev.tradewave.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.androchef.happytimer.utils.extensions.gone
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.common.utils.ValidationHelper
import com.fuadhev.tradewave.databinding.FragmentRegisterBinding
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment :BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    @Inject
    lateinit var helper: ValidationHelper
    private val viewModel by viewModels<AuthViewModel>()
    override fun observeEvents() {
        viewModel.authState.observe(viewLifecycleOwner){
            checkstate(it)
        }
    }

    override fun onCreateFinish() {}

    override fun setupListeners() {
        with(binding){
            btnSingup.setOnClickListener {
                validateData()
            }
        }

    }
    private fun checkstate(state:AuthUiState){
        with(binding){
            when (state) {
                is AuthUiState.SuccessAuth -> {
                    requireActivity().showMessage("Succesfully Sing up", FancyToast.SUCCESS)
                    loading.gone()
                }

                is AuthUiState.Error -> {
                    requireActivity().showMessage(state.message, FancyToast.ERROR)
                    loading.gone()
                }

                is AuthUiState.Loading -> {
                    loading.visible()
                }
            }
        }

    }
    private fun validateData(){
        with(binding){
            if (helper.validateData(etEmail,etPassword,tlEmail,tlPassword)&&helper.validateRegisterData(etPassword,etPassword2,etFullname,tlPassword2,tlFullname)){
                viewModel.registerUser(etEmail.text.toString().trim(),etPassword.text.toString().trim())
            }
        }

    }

}