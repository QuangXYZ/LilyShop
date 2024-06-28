package com.quang.lilyshop.Model

import android.os.Parcel
import android.os.Parcelable

class AddressModel(
    var id: String,
    val fullName: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val district: String = "",
    val ward: String = "",
    val address:String = "",
    val label:String = "",
    val default: Boolean = false,
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

        // Constructor mặc định không tham số (yêu cầu bởi Firebase)
        constructor() : this("", "", "", "","","","","", false)


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(fullName)
        parcel.writeString(phoneNumber)
        parcel.writeString(city)
        parcel.writeString(district)
        parcel.writeString(ward)
        parcel.writeString(address)
        parcel.writeString(label)
        parcel.writeByte(if (default) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressModel> {
        override fun createFromParcel(parcel: Parcel): AddressModel {
            return AddressModel(parcel)
        }

        override fun newArray(size: Int): Array<AddressModel?> {
            return arrayOfNulls(size)
        }
    }
}