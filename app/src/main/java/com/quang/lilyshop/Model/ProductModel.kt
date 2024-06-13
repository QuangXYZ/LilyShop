package com.quang.lilyshop.Model

import android.os.Parcel
import android.os.Parcelable
import android.service.quicksettings.Tile

data class ProductModel(
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var picUrl: ArrayList<String> = ArrayList(),
    var size: ArrayList<String> = ArrayList(),
    var rating: Double = 0.0,
    var numberInCart: Int = 0
):Parcelable{
    constructor(parcel: Parcel):this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, p1: Int) {
     dest.writeString(title)
     dest.writeString(description)
     dest.writeStringList(picUrl)
     dest.writeStringList(size)
     dest.writeDouble(price)
     dest.writeDouble(rating)
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}
