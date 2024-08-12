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
                    0 -> tab.text = "To Pay"
                    1 -> tab.text = "To Ship"
                    2 -> tab.text = "Completed"
                    3 -> tab.text = "Cancelled"
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