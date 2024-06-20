package com.quang.lilyshop.networkService

import com.quang.lilyshop.Model.ApiResponse
import com.quang.lilyshop.Model.DistrictModel
import com.quang.lilyshop.Model.ProvinceModel
import com.quang.lilyshop.Model.WardModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/province/")
    fun getProvinces() : Call<ApiResponse<ProvinceModel>>

    @GET("api/province/district/{province_id}")
    fun getDistrictsByProvince(
        @Path("province_id") province_id: String?
    ) : Call<ApiResponse<DistrictModel>>
   @GET("/api/province/ward/{district_id}")
    fun getWardsByDistrict(
        @Path("district_id") district_id: String?
    ) : Call<ApiResponse<WardModel>>





}