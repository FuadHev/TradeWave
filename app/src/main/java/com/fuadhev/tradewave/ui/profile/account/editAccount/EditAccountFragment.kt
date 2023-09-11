package com.fuadhev.tradewave.ui.profile.account.editAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.showSnack
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.common.utils.InfoEnum
import com.fuadhev.tradewave.databinding.FragmentEditAccountBinding
import com.fuadhev.tradewave.ui.auth.AuthUiState
import com.fuadhev.tradewave.ui.auth.AuthViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditAccountFragment : BaseFragment<FragmentEditAccountBinding>(FragmentEditAccountBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()
    private val args by navArgs<EditAccountFragmentArgs>()
    private var infoText = ""
    override fun observeEvents() {
        viewModel.authState.observe(viewLifecycleOwner){
            handleState(it)
        }
    }

    override fun onCreateFinish() {
        viewModel.getUserInfo()
        binding.info = args.infoName
        handleLayout(args.infoName)
    }
    private fun handleState(state:AuthUiState){
        with(binding){
            when(state){
                is AuthUiState.Loading->{ }

                is AuthUiState.SuccessUpdateInfo->{
                    requireView().showSnack("Updated")
                }

                is AuthUiState.SuccessUserInfo->{
                    user=state.data
                }

                is AuthUiState.Error->{
                    requireActivity().showMessage(state.message,FancyToast.ERROR)
                }

                else -> {}
            }

        }


    }

    private fun handleLayout(info: InfoEnum) {
        with(binding){
            when(info){
                InfoEnum.NAME->{
                    lyName.visible()
                }
                InfoEnum.EMAIL->{
                    lyEmail.visible()
                }
                InfoEnum.GENDER->{
                    setGenderLayout()
                }
                InfoEnum.PASSWORD->{
                    lyPassword.visible()
                }
                InfoEnum.BIRTHDAY->{
                    lyBirthday.visible()
                }
                InfoEnum.PHONENUMBER->{
                    lyPhoneNumber.visible()
                }
            }
        }
    }

    private fun setGenderLayout() {
        with(binding) {
            lyGender.visible()
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                resources.getStringArray(R.array.gender)
            )
            binding.etGender.setAdapter(arrayAdapter)

            etGender.setOnItemClickListener { _, _, position, _ ->
                infoText = arrayAdapter.getItem(position).toString()
            }
        }
    }
    override fun setupListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSave.setOnClickListener {
                when (args.infoName) {
                    InfoEnum.NAME -> {
                        infoText =
                            etFirstName.text.toString().trim() + " " + etLastName.text.toString()
                                .trim()
                    }
                    InfoEnum.EMAIL -> {
                        infoText = etEmail.text.toString().trim()
                    }

                    InfoEnum.BIRTHDAY -> {
                        infoText = etBirthday.text.toString().trim()
                    }

                    InfoEnum.PHONENUMBER -> {
                        infoText = etNumber.text.toString().trim()
                    }
                    else->{}
                }
                if (infoText.isEmpty()) {
                    requireView().showSnack("Field cannot be empty")
                } else {
                    viewModel.updateUser(args.infoName, infoText)
                }
            }

        }
    }

}