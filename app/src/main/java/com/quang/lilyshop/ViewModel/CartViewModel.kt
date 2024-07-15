package com.quang.lilyshop.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.SliderModel
import com.quang.lilyshop.repositoy.AddressRepository

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val managementCart = ManagementCart(application)

    private val cart = MutableLiveData<List<ProductModel>>()
    val carts : LiveData<List<ProductModel>> = cart

    init {
        cart.value = managementCart.getListCart()
    }


}