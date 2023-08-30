package com.fuadhev.tradewave.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.R
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {


    override fun observeEvents() {
    }

    override fun onCreateFinish() {

        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun setupListeners() {
    }


}