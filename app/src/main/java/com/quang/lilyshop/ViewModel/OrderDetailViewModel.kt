package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.repositoy.OrderRepository
import kotlinx.coroutines.launch

class OrderDetailViewModel : ViewModel() {
    private val repository = OrderRepository()
    private val _orders = MutableLiveData<OrderModel>()
    val orders: LiveData<OrderModel>get() = _orders

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun getOrderById(idOrder:String){
        viewModelScope.launch {
            repository.getOrderById(idOrder, {
                _orders.value = it
            }, {
                _error.value = it.toString()

            })
        }
    }
    fun cancelOrder(idOrder:String, onSuccess: () -> Unit){
        viewModelScope.launch {
            repository.cancelOrder(idOrder, {
            }, {
                _error.value = it.toString()

            })
        }
    }

}