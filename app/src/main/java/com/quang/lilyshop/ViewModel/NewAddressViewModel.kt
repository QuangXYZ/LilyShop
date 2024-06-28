package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.repositoy.AddressRepository
import kotlinx.coroutines.launch

class NewAddressViewModel:ViewModel() {
    private val repository = AddressRepository()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun saveUserAddress(address: AddressModel) {
        viewModelScope.launch {
            repository.saveUserAddress(address, {
                // Success
            }, { e ->
                _error.value = e.message
            })
        }
    }


}