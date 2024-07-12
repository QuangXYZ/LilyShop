package com.quang.lilyshop.Helper

import android.content.Context
import android.widget.Toast
import com.quang.lilyshop.Model.ProductModel

class ManagementHistory(val context: Context) {

    private val tinyDB = TinyDB(context)

    fun addRecent(item: String) {
        var listHistory = getListHistory()
        listHistory.add(item)
        tinyDB.putListString("SearchRecent", listHistory)
    }

    fun getListHistory(): ArrayList<String> {
        return tinyDB.getListString("SearchRecent") ?: arrayListOf()
    }

    fun clearListCart() {
        tinyDB.remove("SearchRecent")
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


}