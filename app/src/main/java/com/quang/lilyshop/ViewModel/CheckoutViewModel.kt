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

class CheckoutViewModel: ViewModel() {
    private val addressRepository = AddressRepository()
    private val orderRepository = OrderRepository()

    private val _orderSuccess = MutableLiveData<OrderModel>()
    val orderSuccess: LiveData<OrderModel> get() = _orderSuccess

    private val _orderError = MutableLiveData<Exception>()
    val orderError: LiveData<Exception> get() = _orderError


    fun getDefaultAddress(onSuccess: (AddressModel?) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            addressRepository.getDefaultAddress(
                onSuccess = { address ->
                    onSuccess(address)
                },
                onFailure = { exception ->
                    onFailure(exception)
                }
            )
        }
    }




    fun saveOrder(order: OrderModel) {
        viewModelScope.launch {
            orderRepository.saveOrder(order, onSuccess = {
                _orderSuccess.value = order
            }, onFailure = { exception ->
                _orderError.value = exception
            })
        }
    }


}