package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Helper.DataLocalManager
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.repositoy.OrderRepository
import com.quang.lilyshop.repositoy.ReviewRepository
import kotlinx.coroutines.launch

class ProductRatingViewModel : ViewModel(){
    private val repository = ReviewRepository()
    private val _reviews = MutableLiveData<ReviewModel>()
    val reviews: LiveData<ReviewModel>
        get() = _reviews

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error



    fun addProductReview(review: ReviewModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        review.userName = DataLocalManager.user?.name ?: ""
        review.picUrl = DataLocalManager.user?.picUrl ?: ""

        viewModelScope.launch {
            repository.saveReview(review, {
                onSuccess
            }, {
                _error.value = it.toString()
            })
        }

    }
}