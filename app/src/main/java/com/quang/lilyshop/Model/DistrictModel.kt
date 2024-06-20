package com.quang.lilyshop.Model

data class DistrictModel(
    val district_id: String,
    val district_name: String,
    val district_type: String,
    val lat: Double?,
    val lng: Double?,
    val province_id: String
)