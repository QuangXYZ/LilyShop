package com.quang.lilyshop.Model

import android.service.quicksettings.Tile

data class ProductModel(
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var picUrl: ArrayList<String> = ArrayList(),
    var size: ArrayList<String> = ArrayList(),
    var rating: Double = 0.0,
    var numberInCart: Int = 0
)
