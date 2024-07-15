package com.quang.lilyshop.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.Helper.ManagementHistory

class SearchRecentViewModel (application: Application) : AndroidViewModel(application) {

    private val history = MutableLiveData<List<String>>()
    val histories : LiveData<List<String>> = history
    private val managementHistory = ManagementHistory(application)


    init {
        getRecentSearch()
    }

    fun getRecentSearch() {
        history.value = managementHistory.getListHistory()

    }
    fun clearHistory() {
        managementHistory.clearHistory()
        history.value = managementHistory.getListHistory()
    }
    fun deleteRecentSearch(position:Int) {
        managementHistory.deleteItem(ArrayList(histories.value),position)
        history.value = managementHistory.getListHistory()
    }


}