package com.quang.lilyshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.quang.lilyshop.Adapter.OrderViewPagerAdapter
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.OrderManagerViewModel
import com.quang.lilyshop.databinding.ActivityOrderManagerBinding

class OrderManagerActivity : BaseActivity() {
    private lateinit var binding : ActivityOrderManagerBinding
    private lateinit var viewModel: OrderManagerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingUpListener()
    }

    private fun init() {
        viewModel = OrderManagerViewModel()



        viewModel.orders.observe(this, Observer {

            val adapter = OrderViewPagerAdapter(this,it)
            binding.pager.adapter = adapter
            TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                when (position) {
                    0 -> tab.text = "All"
                    1 -> tab.text = "Pending"
                    2 -> tab.text = "Processing"
                    3 -> tab.text = "Shipped"
                    4 -> tab.text = "Delivered"
                    5 -> tab.text = "Cancelled"

                }
            }.attach()
        })







        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

    }

    private fun settingUpListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}