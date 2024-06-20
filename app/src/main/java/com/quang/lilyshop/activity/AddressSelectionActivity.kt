package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.AddressAdapter
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.databinding.ActivityAddressSelectionBinding

class AddressSelectionActivity : BaseActivity() {
    private lateinit var binding: ActivityAddressSelectionBinding
    private lateinit var address: MutableList<AddressModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAddress()
        settingsUpListener()
    }

    private fun initAddress() {
        address = mutableListOf()
        address.add(
            AddressModel(
                "Nguyen Thanh Quang",
                "01232131",
                "HCM",
                "Cu Chi",
                "TTH",
                "273 An Duong Vuong",
                "Office"
            )
        )
        address.add(
            AddressModel(
                "Nguyen Thanh A",
                "015434334",
                "HCM",
                "Quan 5",
                "Phuong 4",
                "273 An Duong Vuong",
                "Main house"
            )
        )
        binding.viewAddress.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewAddress.adapter = AddressAdapter(address)

    }
    private fun settingsUpListener() {
        binding.addNewAddress.setOnClickListener {
            startActivity(Intent(this, NewAddressActivity::class.java))
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

}