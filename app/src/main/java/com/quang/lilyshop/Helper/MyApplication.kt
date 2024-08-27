package com.quang.lilyshop.Helper

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Khởi tạo DataLocalManager
        DataLocalManager.init(this)
    }
}