package com.quang.lilyshop.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.ActivityNewAddressBinding


class NewAddressActivity : BaseActivity() {
    private lateinit var binding: ActivityNewAddressBinding
    private var home = 0
    private var office = 0

    private var province: String? = null
    private var district: String? = null
    private var ward: String? = null

    private val chooseLocationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            province = result.data?.getStringExtra("province")
            district = result.data?.getStringExtra("district")
            ward = result.data?.getStringExtra("ward")
            val displayText = """
                    $province
                    $district
                    $ward
                    """.trimIndent()
            binding.chooseProvince.setText(displayText)
        }
    }
    private val selectLocationMap = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val street = result.data?.getStringExtra("street")
            val building = result.data?.getStringExtra("building")
            val imageUri = result.data?.getStringExtra("imageUri")
            binding.chooseStreet.setText("$building $street")

            binding.imgMap.setImageURI(imageUri?.toUri())
            binding.imgLayout.visibility = View.VISIBLE
        }
    }

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
            val intent = Intent(this, ChooseLocationActivity::class.java)
            chooseLocationLauncher.launch(intent)
        }

        binding.chooseStreet.setOnClickListener {

            if (province == null) {
                Toast.makeText(this, "Please select your city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, SelectLocationMapActivity::class.java).apply {
                putExtra("province", province)
                putExtra("district", district)
                putExtra("ward", ward)
            }
            selectLocationMap.launch(intent)
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