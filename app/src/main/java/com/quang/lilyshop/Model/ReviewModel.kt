package com.quang.lilyshop.Model

import android.os.Parcel
import android.os.Parcelable

data class ReviewModel(
    var productId: String = "",
    var userId: String = "",
    var userName: String = "",
    var picUrl: String = "",
    var rating: Float = 0.0f,
    var comment: String = "",
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(picUrl)
        parcel.writeFloat(rating)
        parcel.writeString(comment)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewModel> {
        override fun createFromParcel(parcel: Parcel): ReviewModel {
            return ReviewModel(parcel)
        }

        override fun newArray(size: Int): Array<ReviewModel?> {
            return arrayOfNulls(size)
        }
    }
}