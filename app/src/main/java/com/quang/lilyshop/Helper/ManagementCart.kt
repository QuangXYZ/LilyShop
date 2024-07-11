package com.quang.lilyshop.Helper

import android.content.Context
import android.widget.Toast
import com.quang.lilyshop.Model.ProductModel


class ManagementCart(val context: Context) {

    private val tinyDB = TinyDB(context)

    fun insertItem(item: ProductModel) {
        var listFood = getListCart()
        val existAlready = listFood.any { it.title == item.title && it.selectedSize == item.selectedSize }
        val index = listFood.indexOfFirst { it.title == item.title && it.selectedSize == item.selectedSize }

        if (existAlready) {
            listFood[index].numberInCart = item.numberInCart
        } else {
            listFood.add(item)
        }
        tinyDB.putListObject("CartList", listFood)
        Toast.makeText(context, "Added to your Cart ${item.selectedSize}", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ProductModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun clearListCart() {
        tinyDB.remove("CartList")
    }
    fun minusItem(listFood: ArrayList<ProductModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listFood[position].numberInCart == 1) {
            listFood.removeAt(position)
        } else {
            listFood[position].numberInCart--
        }
        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun plusItem(listFood: ArrayList<ProductModel>, position: Int, listener: ChangeNumberItemsListener) {
        listFood[position].numberInCart++
        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }
    fun deleteItem(listFood: ArrayList<ProductModel>, position: Int, listener: ChangeNumberItemsListener) {
        listFood.removeAt(position)
        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun getTotalFee(): Double {
        val listFood = getListCart()
        var fee = 0.0
        for (item in listFood) {
            fee += item.price * item.numberInCart
        }
        return fee
    }
}