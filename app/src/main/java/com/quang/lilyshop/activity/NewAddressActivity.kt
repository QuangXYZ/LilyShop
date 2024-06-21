package com.quang.lilyshop.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.ActivityNewAddressBinding


class NewAddressActivity : BaseActivity() {
    private lateinit var binding: ActivityNewAddressBinding
    private var home = 0
    private var office = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingUpListener()
    }

    private fun init() {

    }

    private fun settingUpListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.home.setOnClickListener {

            home = 1
            office = 0
            binding.home.strokeColor = resources.getColor(R.color.purple)
            binding.office.strokeColor = resources.getColor(R.color.lightGrey)
        }
        binding.office.setOnClickListener {
            home = 0
            office = 1
            binding.office.strokeColor = resources.getColor(R.color.purple)
            binding.home.strokeColor = resources.getColor(R.color.lightGrey)
        }
        binding.chooseProvince.setOnClickListener {
            startActivity(Intent(this, ChooseLocationActivity::class.java))
        }

        binding.chooseStreet.setOnClickListener {
            startActivity(Intent(this, SelectLocationMapActivity::class.java))
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}