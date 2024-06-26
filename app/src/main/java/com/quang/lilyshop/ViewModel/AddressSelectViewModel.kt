package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.repositoy.AddressRepository
import kotlinx.coroutines.launch

class AddressSelectViewModel: ViewModel() {
    private val repository = AddressRepository()
    private val _address = MutableLiveData<List<AddressModel>>()
    val address: LiveData<List<AddressModel>> get() = _address

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        getUserAddress()
    }

    fun getUserAddress() {
        viewModelScope.launch {
            repository.getUserAddress({ address ->
                _address.value = address
            }, { e ->
                _error.value = e.message
            })
        }
    }

}