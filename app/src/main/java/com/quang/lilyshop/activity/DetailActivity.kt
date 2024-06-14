package com.quang.lilyshop.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.ColorAdapter
import com.quang.lilyshop.Adapter.SizeAdapter
import com.quang.lilyshop.Adapter.SliderAdapter
import com.quang.lilyshop.Helper.ManagmentCart
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.SliderModel
import com.quang.lilyshop.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var product: ProductModel
    private var numberOder = 1
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundle()
        banners()
        initLists()


    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in product.size) {
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val  colorList = ArrayList<String>()
        for (imageUrl in product.picUrl) {
            colorList.add(imageUrl)
        }

        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in product.picUrl){

            sliderItems.add(SliderModel(imageUrl))

        }


        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 1

        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle() {
        product = intent.getParcelableExtra("object")!!

        binding.titletxt.text = product.title
        binding.priceTxt.text = "$" + product.price
        binding.descriptionTxt.text = product.description
        binding.productRating.stepSize = 0.01f;
        binding.productRating.numStars = 5
        binding.productRating.rating = product.rating.toFloat()
        binding.addToCartBtn.setOnClickListener {
            product.numberInCart = numberOder
            managmentCart.insertFood(product)
        }
        binding.backBtn.setOnClickListener { finish() }
    }
}