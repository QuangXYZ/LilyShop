package com.quang.lilyshop.repositoy

import com.quang.lilyshop.networkService.ApiService
import com.quang.lilyshop.networkService.RetrofitClient

class AddressRepository {
    fun getProvinces() = RetrofitClient.api.getProvinces()
    fun getDistricts(province_id:String) = RetrofitClient.api.getDistrictsByProvince(province_id)
    fun getWards(district_id:String) = RetrofitClient.api.getWardsByDistrict(district_id)
}


open class abc() {
    class bcd():abc()
}