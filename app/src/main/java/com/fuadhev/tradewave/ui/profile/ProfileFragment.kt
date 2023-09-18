package com.fuadhev.tradewave.ui.profile

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.showSnack
import com.fuadhev.tradewave.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private var isSpinnerInitialized = false
    private lateinit var adapter: ArrayAdapter<String>
    private val viewModel: ProfileViewModel by viewModels()
    override fun observeEvents() {
        viewModel.selectedLanguage.observe(viewLifecycleOwner) {
            setAppLocale(it)
        }
    }

    override fun onCreateFinish() {
        setSpinner()
    }

    override fun setupListeners() {
        with(binding) {
            btnProfile.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAccountFragment())
            }
            btnOrder.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToOrderFragment())
            }
            btnPayment.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAddPaymentFragment())
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long,
                ) {
                    if (isSpinnerInitialized) {
                        val selectedLanguage = adapter.getItem(position).toString()
                        viewModel.saveSelectedLanguage(selectedLanguage)
                        requireView().showSnack(
                            getString(
                                R.string.language_selected_as,
                                selectedLanguage
                            )
                        )
                    } else {
                        isSpinnerInitialized = true
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun setSpinner() {
        with(binding) {
            val languages = resources.getStringArray(R.array.language)
            adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
            spinner.adapter = adapter
            isSpinnerInitialized = false
        }
    }

    private fun setAppLocale(locale: Locale) {
        val resources = requireContext().resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}