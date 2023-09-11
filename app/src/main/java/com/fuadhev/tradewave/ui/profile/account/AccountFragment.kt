package com.fuadhev.tradewave.ui.profile.account


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.showSnack
import com.fuadhev.tradewave.common.utils.Extensions.toEditFragment
import com.fuadhev.tradewave.common.utils.InfoEnum
import com.fuadhev.tradewave.databinding.FragmentAccountBinding
import com.fuadhev.tradewave.ui.auth.AuthUiState
import com.fuadhev.tradewave.ui.auth.AuthViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {
    private val authViewModel: AuthViewModel by viewModels()

    override fun observeEvents() {
        with(authViewModel){
            authState.observe(viewLifecycleOwner){
                handleState(it)
            }
        }
    }

    private fun handleState(state: AuthUiState) {
        when(state){
            is AuthUiState.SuccessAuth->{
                requireView().showSnack("Succesfully log out")
                findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToLoginFragment())
            }
            is AuthUiState.Error->{
                requireActivity().showMessage(state.message, FancyToast.ERROR)
            }
            is AuthUiState.SuccessUserInfo->{
                binding.user=state.data
            }
            is AuthUiState.Loading->{}

            else -> {}
        }
    }

    override fun onCreateFinish() {
        authViewModel.getUserInfo()
    }

    override fun setupListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvName.setOnClickListener {
                this@AccountFragment.toEditFragment(InfoEnum.NAME)
            }
            lyEmail.setOnClickListener {
                this@AccountFragment.toEditFragment(InfoEnum.EMAIL)
            }
            lyBirthday.setOnClickListener {
                this@AccountFragment.toEditFragment(InfoEnum.BIRTHDAY)
            }
            lyGender.setOnClickListener {
                this@AccountFragment.toEditFragment(InfoEnum.GENDER)
            }
            lyPassword.setOnClickListener {
                this@AccountFragment.toEditFragment(InfoEnum.PASSWORD)
            }
            lyNumber.setOnClickListener {
                this@AccountFragment.toEditFragment(InfoEnum.PHONENUMBER)
            }
            btnLogout.setOnClickListener {
                authViewModel.logoutUser()
            }
        }
    }



}