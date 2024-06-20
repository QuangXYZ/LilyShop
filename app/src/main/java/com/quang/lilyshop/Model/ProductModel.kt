package com.quang.lilyshop.Model

import android.os.Parcel
import android.os.Parcelable

data class ProductModel(
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var size: ArrayList<String> = ArrayList(),
    var rating: Double = 0.0,
    var price: Double = 0.0,
    var numberInCart: Int = 0,
    var selectedSize:String = ""
):Parcelable{
    constructor(parcel: Parcel):this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
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
     dest.writeDouble(rating)
     dest.writeDouble(price)
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
