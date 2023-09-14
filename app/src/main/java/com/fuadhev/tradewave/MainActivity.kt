package com.fuadhev.tradewave

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.fuadhev.tradewave.databinding.ActivityMainBinding
import com.fuadhev.tradewave.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel by viewModels<ProfileViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loadSelectedLanguage()
        observeEvents()
        setBottomView()
    }

    fun observeEvents(){
        viewModel.selectedLanguage.observe(this){
            setLanguage(it)
        }
    }
    private fun setLanguage(locale: Locale) {
        val resources = this.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    private fun setBottomView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.botomMenu, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment,
                R.id.loginFragment,
                R.id.registerFragment,
//                R.id.offerProductFragment,
//                R.id.favouriteFragment,
                R.id.detailFragment,
                -> binding.botomMenu.visibility = View.GONE

                else -> binding.botomMenu.visibility = View.VISIBLE
            }
        }

    }

}