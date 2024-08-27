package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseError
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.repositoy.ReviewRepository

class HistoryReviewViewModel : ViewModel() {
    private val repository = ReviewRepository()
    private val _review = MutableLiveData<List<ReviewModel>>()
    val review: LiveData<List<ReviewModel>>
        get() = _review


    fun getReviewByProduct (products: ArrayList<ProductModel>, onSuccess: (List<ReviewModel>) -> Unit, onFailure: (DatabaseError) -> Unit) {
        repository.getReviewByProductList(products, {
                                                    onSuccess(it)

        }, {
            onFailure(it)

        }
        )
    }


}