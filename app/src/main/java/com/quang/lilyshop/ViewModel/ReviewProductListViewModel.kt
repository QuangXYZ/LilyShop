package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.repositoy.ReviewRepository

class ReviewProductListViewModel : ViewModel() {
    private val repository = ReviewRepository()
    private val _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>>
        get() = _products

    init {
        getProductsForReview()
    }

    fun getProductsForReview () {
        repository.getProductForReview( { product ->
            _products.value = product
        }, {

        }
        )
    }


}