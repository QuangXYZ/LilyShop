package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Model.BrandModel
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.SliderModel

class MainViewModel: ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val banner = MutableLiveData<List<SliderModel>>()
    val banners : LiveData<List<SliderModel>> = banner

    private val brand = MutableLiveData<MutableList<BrandModel>>()
    val brands : LiveData<MutableList<BrandModel>> = brand

    private val product = MutableLiveData<MutableList<ProductModel>>()
    val products : LiveData<MutableList<ProductModel>> = product


    fun loadBanner() {
        val bannerRef = firebaseDatabase.getReference("Banner")
        bannerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<SliderModel>()
                for (item in snapshot.children) {
                    val banner = item.getValue(SliderModel::class.java)
                    if (banner != null) list.add(banner)
                }
                banner.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun loadBrand() {
        val brandRef = firebaseDatabase.getReference("Category")
        brandRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<BrandModel>()
                for (item in snapshot.children) {
                    val brand = item.getValue(BrandModel::class.java)
                    if (brand != null) list.add(brand)
                }
                brand.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun loadProduct() {
        val productRef = firebaseDatabase.getReference("Items")
        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<ProductModel>()
                for (item in snapshot.children) {
                    val product = item.getValue(ProductModel::class.java)
                    if (product != null) list.add(product)
                }
                product.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}