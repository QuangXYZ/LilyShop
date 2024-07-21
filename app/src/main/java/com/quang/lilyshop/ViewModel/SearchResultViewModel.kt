package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.activity.Fragment.SearchResultFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SearchResultViewModel: ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val product = MutableLiveData<List<ProductModel>>()
    val products : LiveData<List<ProductModel>> = product

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun searchResult(query: String, state : SearchResultFragment.SortState) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = fetchProducts(query)

            val sortedResult = when (state) {
                SearchResultFragment.SortState.NONE -> result
                SearchResultFragment.SortState.ASCENDING -> result.sortedBy { it.price }
                SearchResultFragment.SortState.DESCENDING -> result.sortedByDescending { it.price }
            }
            product.value = sortedResult
            _isLoading.value = false
        }
    }

    private suspend fun fetchProducts(query: String): List<ProductModel>  {
        return withContext(Dispatchers.IO) {
            val productRef = firebaseDatabase.getReference("Items")
            try {
                val snapshot = productRef.get().await()
                val list = mutableListOf<ProductModel>()
                for (item in snapshot.children) {
                    val product = item.getValue(ProductModel::class.java)
                    if (product != null && (product.title.contains(query, true) || product.description.contains(query, true)) ) {
                        list.add(product)
                    }
                }
                list
            } catch (e: Exception) {
                emptyList()
            }
        }
    }



}