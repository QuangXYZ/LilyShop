package com.quang.lilyshop.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.ProductRatingViewModel
import com.quang.lilyshop.databinding.ActivityProductRatingBinding

class ProductRatingActivity : BaseActivity() {
    private lateinit var binding : ActivityProductRatingBinding
    private lateinit var viewModel: ProductRatingViewModel
    private lateinit var product : ProductModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingUpListener()
    }

    private fun init() {
        viewModel = ProductRatingViewModel()
        product = intent.getParcelableExtra("product")!!

        binding.productName.text = product.title
        binding.productRating.text = product.rating.toString()
        binding.productPrice.text = product.price.toString()
        Glide.with(this)
            .load(product.picUrl[0])
            .centerInside()
            .into(binding.productImg)


    }
    private fun settingUpListener() {

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.submitBtn.setOnClickListener {
            val review = ReviewModel()
            review.productId = product.id
            review.rating = binding.ratingBar.rating
            review.comment = binding.comment.text.toString()


            viewModel.addProductReview(review, {
                finish()
            }, {

            })

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