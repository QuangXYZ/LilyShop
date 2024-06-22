package com.quang.lilyshop.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lriccardo.timelineview.TimelineDecorator
import com.quang.lilyshop.Adapter.AddressLineAdapter
import com.quang.lilyshop.Adapter.DistrictAdapter
import com.quang.lilyshop.Adapter.ProvinceAdapter
import com.quang.lilyshop.Adapter.SliderAdapter
import com.quang.lilyshop.Adapter.WardAdapter
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.Model.BrandModel
import com.quang.lilyshop.Model.DistrictModel
import com.quang.lilyshop.Model.ProvinceModel
import com.quang.lilyshop.Model.WardModel
import com.quang.lilyshop.ViewModel.ChooseLocationViewModel
import com.quang.lilyshop.databinding.ActivityChooseLocationBinding
import com.quang.lilyshop.networkService.ApiState

class ChooseLocationActivity : BaseActivity() {
    private lateinit var binding: ActivityChooseLocationBinding
    private lateinit var address: MutableList<String>
    private lateinit var addressAdapter: AddressLineAdapter

    private lateinit var provinces: MutableList<ProvinceModel>
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var districtAdapter: DistrictAdapter
    private lateinit var wardAdapter: WardAdapter
    private lateinit var provinceId: String
    private lateinit var districtId: String


    private val viewModel = ChooseLocationViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        loadProvinces()

    }

    private fun loadProvinces() {
        binding.listAddress.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration =
            DividerItemDecoration(binding.listAddress.context, LinearLayoutManager.VERTICAL)
        binding.listAddress.addItemDecoration(dividerItemDecoration)
        viewModel.provinces.observe(this, Observer { state ->
            when (state) {
                is ApiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.listAddress.visibility = View.GONE
                }

                is ApiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.listAddress.visibility = View.VISIBLE
                    provinces = state.data.toMutableList()
                    provinceAdapter = ProvinceAdapter(provinces, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            address.clear()
                            address.add(provinces[position].province_name)
                            address.add("Select District")
                            addressAdapter.select(1)
                            binding.addressLabel.text = "District"
                            addressAdapter.notifyDataSetChanged()
                            viewModel.fetchDistricts(provinces[position].province_id)
                            provinceId = provinces[position].province_id
                            loadDistricts()
                        }
                    })
                    binding.listAddress.adapter = provinceAdapter
                }

                is ApiState.Error -> {

                }

            }
        })
    }

    private fun loadDistricts() {
        binding.listAddress.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration =
            DividerItemDecoration(binding.listAddress.context, LinearLayoutManager.VERTICAL)
        binding.listAddress.addItemDecoration(dividerItemDecoration)
        viewModel.districts.observe(this, Observer { state ->
            when (state) {
                is ApiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.listAddress.visibility = View.GONE
                }

                is ApiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.listAddress.visibility = View.VISIBLE
                    districtAdapter = DistrictAdapter(state.data, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {

                            var tmp = address[0]
                            address.clear()
                            address.add(tmp)
                            address.add(state.data[position].district_name)
                            address.add("Select Ward")
                            addressAdapter.select(2)
                            binding.addressLabel.text = "Ward"
                            addressAdapter.notifyDataSetChanged()
                            viewModel.fetchWards(state.data[position].district_id)
                            districtId = state.data[position].district_id

                            loadWards()
                        }
                    })
                    binding.listAddress.adapter = districtAdapter
                }

                is ApiState.Error -> {

                }

            }
        })
    }

    private fun loadWards() {
        binding.listAddress.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration =
            DividerItemDecoration(binding.listAddress.context, LinearLayoutManager.VERTICAL)
        binding.listAddress.addItemDecoration(dividerItemDecoration)
        viewModel.ward.observe(this, Observer { state ->
            when (state) {
                is ApiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.listAddress.visibility = View.GONE
                }

                is ApiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.listAddress.visibility = View.VISIBLE
                    wardAdapter = WardAdapter(state.data, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            address.removeLast()
                            address.add(state.data[position].ward_name)
                            addressAdapter.notifyDataSetChanged()
                            val intent = Intent()
                            intent.putExtra("province", address[0])
                            intent.putExtra("district", address[1])
                            intent.putExtra("ward", address[2])
                            setResult(RESULT_OK, intent)
                            finish()


                        }

                    })
                    binding.listAddress.adapter = wardAdapter
                }

                is ApiState.Error -> {

                }

            }
        })
    }

    private fun init() {

        address = mutableListOf()
        address.add("Select City")

        addressAdapter = AddressLineAdapter(address, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> {
                        if (binding.addressLabel.text != "City") {
                            loadProvinces()
                            binding.addressLabel.text = "City"
                        }

                    }

                    1 -> {
                        binding.addressLabel.text = "District"
                        viewModel.fetchDistricts(provinceId)
                    }

                    2 -> {
                        binding.addressLabel.text = "Ward"
                        viewModel.fetchWards(districtId)
                    }
                }

            }
        })

        binding.chooseAddress.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.chooseAddress.addItemDecoration(
            TimelineDecorator(
                indicatorSize = 12f,
                lineWidth = 4f,
                padding = 8f,
                position = TimelineDecorator.Position.Left,
                indicatorColor = Color.RED,
                lineColor = Color.RED
            )
        )
        binding.chooseAddress.adapter = addressAdapter



        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    provinceAdapter.filter(newText.toString())
                    districtAdapter.filter(newText.toString())
                    wardAdapter.filter(newText.toString())
                } catch (e: Exception) {
                }

                return true

            }

        })
        binding.backBtn.setOnClickListener {
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