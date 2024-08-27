package com.quang.lilyshop.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.quang.lilyshop.Adapter.OrderViewPagerAdapter
import com.quang.lilyshop.Adapter.ProductReviewAdapter
import com.quang.lilyshop.Adapter.ReviewViewPagerAdapter
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.ViewModel.ReviewProductListViewModel
import com.quang.lilyshop.databinding.ActivityReviewProductListBinding

class ReviewManagerActivity : BaseActivity() {
    private lateinit var binding: ActivityReviewProductListBinding
    private lateinit var viewModel: ReviewProductListViewModel
    private var products: List<ProductModel>? = null
    private var history: List<ProductModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingUpListener()

    }

    private fun init() {
        viewModel = ReviewProductListViewModel()



        viewModel.products.observe(this, Observer {
            products = it
            loadData()
        })
        viewModel.history.observe(this, Observer {
            history = it
            loadData()
        })


    }
    private fun settingUpListener () {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun loadData() {
        val adapter = ReviewViewPagerAdapter(this,products, history)
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Add Review"
                1 -> tab.text = "Review History"
            }
        }.attach()
    }


}