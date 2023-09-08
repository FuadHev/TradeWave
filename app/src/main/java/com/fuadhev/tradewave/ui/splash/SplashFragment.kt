package com.fuadhev.tradewave.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.setStatusColor
import com.fuadhev.tradewave.common.utils.ValidationHelper
import com.fuadhev.tradewave.databinding.FragmentSplashBinding
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {


    @Inject
    lateinit var helper: ValidationHelper
    private val viewModel by viewModels<SplashViewModel> ()
    private var auth=false
    override fun observeEvents() {
        viewModel.authData.observe(viewLifecycleOwner){
            auth=it
            Log.e("auth", auth.toString() )
        }
    }

    override fun onCreateFinish() {
        requireActivity().setStatusColor(R.color.blue)
        lifecycleScope.launch {
            delay(2000)
            if (!helper.isInternetAvailable()) {
                showNoInternetDialog()
            }else{

                if (auth) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                } else {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }
            }
            requireActivity().setStatusColor(R.color.white)
        }
    }

    override fun setupListeners() {
    }

    private fun showNoInternetDialog() {
        AestheticDialog.Builder(requireActivity(), DialogStyle.CONNECTIFY, DialogType.ERROR)
            .setTitle("No Internet Connection")
            .setMessage("Please check your connection")
            .setCancelable(false)
            .setDarkMode(false)
            .setGravity(Gravity.BOTTOM)
            .setAnimation(DialogAnimation.SHRINK)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    requireActivity().finish()
                }
            })
            .show()
    }

}