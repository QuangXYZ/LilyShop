package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.repositoy.AddressRepository
import com.quang.lilyshop.repositoy.OrderRepository
import kotlinx.coroutines.launch

class OrderManagerViewModel : ViewModel(){
    private val repository = OrderRepository()
    private val _orders = MutableLiveData<List<OrderModel>>()
    val orders: LiveData<List<OrderModel>> get() = _orders

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    init {
        getUserOrder()
    }

    fun getUserOrder(){
        viewModelScope.launch {
            repository.getUserOrders({
                _orders.value = it
            }, {
                _error.value = it.toString()

            })
        }
    }

}