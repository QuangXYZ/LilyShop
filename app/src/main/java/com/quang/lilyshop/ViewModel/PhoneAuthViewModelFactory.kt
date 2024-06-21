package com.quang.lilyshop.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quang.lilyshop.repositoy.PhoneAuthRepository

class PhoneAuthViewModelFactory(private val repository: PhoneAuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhoneAuthViewModel::class.java)) {
            return PhoneAuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}