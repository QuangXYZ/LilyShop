package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.repositoy.AddressRepository

class NewAddressViewModel:ViewModel() {
    private val repository = AddressRepository()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun saveUserAddress(address: AddressModel) {
        repository.saveUserAddress(address, {
            // Success
        }, { e ->
            _error.value = e.message
        })
    }


}