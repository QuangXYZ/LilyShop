package com.quang.lilyshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.OrderDetailAdapter
import com.quang.lilyshop.Adapter.ProductReviewAdapter
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.ReviewProductListViewModel
import com.quang.lilyshop.databinding.ActivityReviewProductListBinding

class ReviewProductListActivity : BaseActivity() {
    private lateinit var binding: ActivityReviewProductListBinding
    private lateinit var viewModel: ReviewProductListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        viewModel = ReviewProductListViewModel()
        viewModel.products.observe(this, Observer {
            binding.productList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.productList.adapter =
                ProductReviewAdapter(it as MutableList<ProductModel>)
        })


    }
    private fun settingUpListener () {
        // TODO: Add your listener here.
    }


}