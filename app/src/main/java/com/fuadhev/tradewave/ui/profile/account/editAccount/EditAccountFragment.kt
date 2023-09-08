package com.fuadhev.tradewave.ui.profile.account.editAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.common.utils.InfoEnum
import com.fuadhev.tradewave.databinding.FragmentEditAccountBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditAccountFragment : BaseFragment<FragmentEditAccountBinding>(FragmentEditAccountBinding::inflate) {


    private val args by navArgs<EditAccountFragmentArgs>()

    override fun observeEvents() {

    }

    override fun onCreateFinish() {
        val info = args.infoName
        binding.info = info
        handleLayout(info)
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
        with(binding){
            lyGender.visible()
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.gender))
            binding.autoCompleteTextView.setAdapter(arrayAdapter)

            autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                val selectedGender = arrayAdapter.getItem(position).toString()

            }
        }
    }

    override fun setupListeners() {
        with(binding){
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

}