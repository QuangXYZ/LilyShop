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
import androidx.lifecycle.Observer
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.NewAddressViewModel
import com.quang.lilyshop.databinding.ActivityNewAddressBinding


class NewAddressActivity : BaseActivity() {
    private lateinit var binding: ActivityNewAddressBinding
    private lateinit var viewModel: NewAddressViewModel
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
        viewModel = NewAddressViewModel()
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })


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

        binding.submitBtn.setOnClickListener {
            if (binding.name.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.phone.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.chooseProvince.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select your city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.chooseStreet.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select your street", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (home == 0 && office == 0) {
                Toast.makeText(this, "Please select your label", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val address:AddressModel = AddressModel(
                "",
                binding.name.text.toString(),
                binding.phone.text.toString(),
                province.toString(),
                district.toString(),
                ward.toString(),
                binding.chooseStreet.text.toString(),
                if (home == 1)  "Home" else "Office",
                binding.switcher.isChecked
            )
            viewModel.saveUserAddress(address)
            finish()
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