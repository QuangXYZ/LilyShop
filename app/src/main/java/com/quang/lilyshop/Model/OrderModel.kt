package com.quang.lilyshop.Model

import android.os.Parcel
import android.os.Parcelable

data class OrderModel(
    var id: String = "",
    var userId: String = "",
    var products: List<ProductModel> = listOf(),
    val status: OrderStatus,
    var shippingFee: Double = 0.0,
    var tax: Double = 0.0,
    var totalPrice: Double = 0.0,
    var timestamp: Long = System.currentTimeMillis()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createTypedArrayList(ProductModel) ?: listOf(),
        OrderStatus.valueOf(parcel.readString() ?: OrderStatus.PENDING.name),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readLong()
    ) {
    }

    constructor() : this("", "", listOf(), OrderStatus.PENDING,0.0,0.0,0.0, System.currentTimeMillis())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeTypedList(products)
        parcel.writeString(status.name)
        parcel.writeDouble(shippingFee)
        parcel.writeDouble(tax)
        parcel.writeDouble(totalPrice)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }
}