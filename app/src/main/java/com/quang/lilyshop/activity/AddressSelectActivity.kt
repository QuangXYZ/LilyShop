package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.AddressAdapter
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.ViewModel.AddressSelectViewModel
import com.quang.lilyshop.databinding.ActivityAddressSelectionBinding

class AddressSelectActivity : BaseActivity() {
    private lateinit var binding: ActivityAddressSelectionBinding
    private lateinit var address: MutableList<AddressModel>
    private lateinit var viewModel: AddressSelectViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingsUpListener()
    }

    private fun init() {
        viewModel = AddressSelectViewModel()

        viewModel.address.observe(this, Observer {
            address = it.sortedByDescending { it.default }.toMutableList()
            binding.viewAddress.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.viewAddress.adapter = AddressAdapter(address)

        })


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