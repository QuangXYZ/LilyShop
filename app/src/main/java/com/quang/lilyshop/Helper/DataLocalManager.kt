package com.quang.lilyshop.Helper


import android.content.Context

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.quang.lilyshop.Model.User
import com.quang.lilyshop.Model.UserModel


class DataLocalManager {
    private var mySharedPreferences: MySharedPreferences? = null

    companion object {
        private const val PREF_USER = "PREF_USER"

        private var instance: DataLocalManager? = null
        fun init(context: Context?) {
            instance = DataLocalManager()
            instance!!.mySharedPreferences = MySharedPreferences(context!!)
        }

        fun getInstance(): DataLocalManager? {
            if (instance == null) {
                instance = DataLocalManager()
            }
            return instance
        }


        var user: UserModel?
            get() {
                val strUser =
                    getInstance()!!.mySharedPreferences!!.getStringValue(
                        PREF_USER
                    )
                val gson = Gson()
                return gson.fromJson(
                    strUser,
                    UserModel::class.java
                )
            }
            set(user) {
                val gson = Gson()
                val strJsonUser = gson.toJson(user)
                getInstance()!!.mySharedPreferences!!.putStringValue(PREF_USER, strJsonUser)
            }

    }
}