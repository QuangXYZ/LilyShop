package com.quang.lilyshop.Helper

import android.content.Context
import android.widget.Toast
import com.quang.lilyshop.Model.ProductModel

class ManagementHistory(val context: Context) {
    private val TAG = "SearchRecent"

    private val tinyDB = TinyDB(context)

    fun addRecent(item: String) {
        var listHistory = getListHistory()
        listHistory.add(item)
        tinyDB.putListString(TAG, listHistory)
    }

    fun getListHistory(): ArrayList<String> {
        return tinyDB.getListString(TAG) ?: arrayListOf()
    }

    fun clearHistory() {
        tinyDB.remove(TAG)
    }



    fun deleteItem(listHistory: ArrayList<String>, position: Int) {
        listHistory.removeAt(position)
        tinyDB.putListString(TAG, listHistory)
    }


}