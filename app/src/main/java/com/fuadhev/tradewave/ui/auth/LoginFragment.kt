package com.fuadhev.tradewave.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androchef.happytimer.utils.extensions.gone
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.common.utils.ValidationHelper
import com.fuadhev.tradewave.databinding.FragmentLoginBinding
import com.fuadhev.tradewave.domain.model.UserUiModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var helper: ValidationHelper
    private val viewModel by viewModels<AuthViewModel>()

    override fun observeEvents() {
        with(viewModel) {
            authState.observe(viewLifecycleOwner) {
                checkState(it)
            }
        }

    }

    override fun onCreateFinish() {


    }

    override fun setupListeners() {
        with(binding) {
            btnSingin.setOnClickListener {
                validateData()
            }
            txtSingup.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun validateData() {

        with(binding) {
            if (helper.validateData(etEmail, etPassword, tlEmail, tlPassword)) {
                viewModel.loginUser(etEmail.text.toString().trim(), etPassword.text.toString().trim(),
                    UserUiModel(uid=etEmail.text.toString().trim(), email = etEmail.text.toString().trim())
                )
            }
        }
    }

    private fun checkState(state: AuthUiState) {
        with(binding) {
            when (state) {
                is AuthUiState.SuccessAuth -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    requireActivity().showMessage("Succesfully Sing in", FancyToast.SUCCESS)
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


}