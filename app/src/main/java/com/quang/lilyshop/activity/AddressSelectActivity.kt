package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.AddressAdapter
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.ViewModel.AddressSelectViewModel
import com.quang.lilyshop.databinding.ActivityAddressSelectionBinding

class AddressSelectActivity : BaseActivity() {
    private lateinit var binding: ActivityAddressSelectionBinding
    private lateinit var address: MutableList<AddressModel>
    private lateinit var viewModel: AddressSelectViewModel
    private lateinit var addressAdapter: AddressAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingsUpListener()
    }

    private fun init() {
        val addressSelect:AddressModel? = intent.getParcelableExtra("address")

        viewModel = AddressSelectViewModel()

        viewModel.address.observe(this, Observer {
            address = it.sortedByDescending { it.default }.toMutableList()
            binding.viewAddress.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            addressAdapter = AddressAdapter(address, object: OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent()
                    intent.putExtra("address", address[position])
                    setResult(RESULT_OK, intent)
                    finish()
                }
            })
            if (addressSelect != null) {
                val selectedIndex = address.indexOfFirst {
                        it.id  == addressSelect.id}
                addressAdapter.select(selectedIndex)

            }
            binding.viewAddress.adapter = addressAdapter

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