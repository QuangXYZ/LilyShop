package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Helper.DataLocalManager
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.repositoy.ReviewRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {
    private val repository = ReviewRepository()
    private val _reviews = MutableLiveData<List<ReviewModel>>()
    val reviews: LiveData<List<ReviewModel>>
        get() = _reviews

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error



    fun loadReview(
        productId: String,

        ) {

        viewModelScope.launch {
            repository.getReviewsByProduct(productId, {
                _reviews.value = it
            }, {
                _error.value = it.toString()
            })
        }

    }
}