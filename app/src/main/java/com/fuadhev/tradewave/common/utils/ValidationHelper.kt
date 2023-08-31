package com.fuadhev.tradewave.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Patterns
import android.widget.EditText
import com.fuadhev.tradewave.R
import com.google.android.material.textfield.TextInputLayout


class ValidationHelper(val context: Context) {

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
    private fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun validateData(
        emailEditText: EditText,
        passwordEditText: EditText,
        emailLy: TextInputLayout,
        passLy: TextInputLayout,
    ): Boolean {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()


        if (!isEmailValid(email)) {
            emailLy.apply {
                isErrorEnabled = true
                error = context.getString(R.string.invalid_email_format)
            }
            return false
        } else {
            emailLy.isErrorEnabled = false
        }

        if (!isPasswordValid(password)) {
            passLy.apply {
                isErrorEnabled = true

                error = context.getString(R.string.password_required)
            }
            return false
        } else {
            passLy.isErrorEnabled = false
        }

        return true
    }

    fun validateRegisterData(
        etPass1: EditText,
        etPass2: EditText,
        etName: EditText,
        lyPass2: TextInputLayout,
        lyName:TextInputLayout
    ): Boolean {
        if (etPass1.text.toString().trim() != etPass2.text.toString().trim()) {
            lyPass2.apply {
                isErrorEnabled = true
                error = "Password does not match"
            }
            return false
        }

        if (etName.text.toString().isEmpty()) {
            lyName.apply {
                isErrorEnabled = true
                error = "Name required"
            }
            return false
        }

        lyPass2.isErrorEnabled = false
        return true
    }

}
