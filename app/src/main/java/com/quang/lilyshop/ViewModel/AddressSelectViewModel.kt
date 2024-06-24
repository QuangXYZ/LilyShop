package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.repositoy.AddressRepository

class AddressSelectViewModel {
    private val repository = AddressRepository()
    private val _address = MutableLiveData<List<AddressModel>>()
    val address: LiveData<List<AddressModel>> get() = _address

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        getUserAddress()
    }

    fun getUserAddress() {
        repository.getUserAddress({ address ->
            _address.value = address
        }, { e ->
            _error.value = e.message
        })
    }

}