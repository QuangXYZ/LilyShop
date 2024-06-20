package com.quang.lilyshop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.lilyshop.Model.ApiResponse
import com.quang.lilyshop.Model.DistrictModel
import com.quang.lilyshop.Model.ProvinceModel
import com.quang.lilyshop.Model.WardModel
import com.quang.lilyshop.networkService.ApiState
import com.quang.lilyshop.repositoy.AddressRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseLocationViewModel:ViewModel() {

    private val repository = AddressRepository()
    private val _provinces = MutableLiveData<ApiState<List<ProvinceModel>>>()
    val provinces: LiveData<ApiState<List<ProvinceModel>>> get() = _provinces

    private val _districts = MutableLiveData<ApiState<List<DistrictModel>>>()
    val districts: LiveData<ApiState<List<DistrictModel>>> get() = _districts

    private val _wards = MutableLiveData<ApiState<List<WardModel>>>()
    val ward: LiveData<ApiState<List<WardModel>>> get() = _wards


    init {
        fetchProvinces()
    }

//    private fun fetchProvinces()  {
    private fun fetchProvinces() = viewModelScope.launch {
        _provinces.postValue(ApiState.Loading)
        repository.getProvinces().enqueue(object : Callback<ApiResponse<ProvinceModel>> {
            override fun onResponse(call: Call<ApiResponse<ProvinceModel>>, response: Response<ApiResponse<ProvinceModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        _provinces.postValue(ApiState.Success(result.results))
                    }
                } else {
                    _provinces.postValue(ApiState.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ApiResponse<ProvinceModel>>, t: Throwable) {
                _provinces.postValue(ApiState.Error(t.message ?: "An unknown error occurred"))
            }
        })
    }
    fun fetchDistricts(province_id:String) = viewModelScope.launch {
        _districts.postValue(ApiState.Loading)
        repository.getDistricts(province_id).enqueue(object : Callback<ApiResponse<DistrictModel>> {
            override fun onResponse(call: Call<ApiResponse<DistrictModel>>, response: Response<ApiResponse<DistrictModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        _districts.postValue(ApiState.Success(result.results))
                    }
                } else {
                    _districts.postValue(ApiState.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ApiResponse<DistrictModel>>, t: Throwable) {
                _districts.postValue(ApiState.Error(t.message ?: "An unknown error occurred"))
            }
        })
    }
    fun fetchWards(district_id:String) = viewModelScope.launch {
        _wards.postValue(ApiState.Loading)
        repository.getWards(district_id).enqueue(object : Callback<ApiResponse<WardModel>> {
            override fun onResponse(call: Call<ApiResponse<WardModel>>, response: Response<ApiResponse<WardModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        _wards.postValue(ApiState.Success(result.results))
                    }
                } else {
                    _wards.postValue(ApiState.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<ApiResponse<WardModel>>, t: Throwable) {
                _wards.postValue(ApiState.Error(t.message ?: "An unknown error occurred"))
            }
        })
    }


}