package com.fuadhev.tradewave.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuadhev.tradewave.common.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intellij.lang.annotations.Language
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val sp:SharedPrefManager) :ViewModel() {
    private val _selectedLanguage = MutableLiveData<Locale>()
    val selectedLanguage : LiveData<Locale> get() = _selectedLanguage


    fun saveSelectedLanguage(selectedLanguage: String) {
        sp.saveLang(selectedLanguage)
        updateSelectedLanguage(selectedLanguage)
    }

    fun loadSelectedLanguage() {
        val selectedLanguage = sp.getLang()
        updateSelectedLanguage(selectedLanguage)
    }

    private fun updateSelectedLanguage(languageCode: String?) {
        val locale = when (languageCode) {
            "Eng" -> Locale("en")
            "İng" -> Locale("en")
            "Aze" -> Locale("az")
            else -> Locale("en")
        }
        _selectedLanguage.value = locale
    }
}