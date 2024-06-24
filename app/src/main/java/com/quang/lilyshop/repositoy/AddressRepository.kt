package com.quang.lilyshop.repositoy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.networkService.ApiService
import com.quang.lilyshop.networkService.RetrofitClient

class AddressRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()


    fun getProvinces() = RetrofitClient.api.getProvinces()
    fun getDistricts(province_id:String) = RetrofitClient.api.getDistrictsByProvince(province_id)
    fun getWards(district_id:String) = RetrofitClient.api.getWardsByDistrict(district_id)


    // Hàm để lấy địa chỉ của người dùng đã đăng nhập
    fun getUserAddress(onSuccess: (List<AddressModel>) -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return  // Lấy ID của người dùng hiện tại
        val addressesRef = database.child("users").child(userId).child("address")

        addressesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val addresses = mutableListOf<AddressModel>()
                for (snapshot in dataSnapshot.children) {
                    val address = snapshot.getValue(AddressModel::class.java)
                    address?.let { addresses.add(it) }
                }
                onSuccess(addresses)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(Exception(databaseError.message))
            }
        })
    }

    // Hàm để lấy địa chỉ của người dùng đã đăng nhập
    fun saveUserAddress(address: AddressModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            val userId = auth.currentUser?.uid ?: return  // Lấy ID của người dùng hiện tại
            val addressesRef = database.child("users").child(userId).child("address")

            if (address.default) {
                // Nếu địa chỉ mới là mặc định, cập nhật tất cả các địa chỉ hiện tại thành không mặc định
                addressesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val updates = mutableMapOf<String, Any?>()
                        dataSnapshot.children.forEach { snapshot ->
                            updates[snapshot.key!! + "/default"] = false
                        }
                        addressesRef.updateChildren(updates)
                            .addOnSuccessListener {
                                // Sau khi cập nhật, thêm địa chỉ mới
                                addressesRef.push().setValue(address)
                                    .addOnSuccessListener { onSuccess() }
                                    .addOnFailureListener { e -> onFailure(e) }
                            }
                            .addOnFailureListener { e -> onFailure(e) }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        onFailure(Exception(databaseError.message))
                    }
                })
            } else {
                // Nếu địa chỉ mới không phải là mặc định, thêm địa chỉ mới vào
                addressesRef.push().setValue(address)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onFailure(e) }
            }
        }



}

