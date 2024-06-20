package com.quang.lilyshop.activity.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.quang.lilyshop.Adapter.BrandAdapter
import com.quang.lilyshop.Adapter.ProductAdapter
import com.quang.lilyshop.Adapter.SliderAdapter
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.Model.BrandModel
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.SliderModel
import com.quang.lilyshop.ViewModel.MainViewModel
import com.quang.lilyshop.activity.CartActivity
import com.quang.lilyshop.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel = MainViewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var brands: MutableList<BrandModel>
    private lateinit var managementCart: ManagementCart

    private lateinit var productAdapter: ProductAdapter
    private lateinit var products: MutableList<ProductModel>

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val itemCount = sliderAdapter.itemCount
            val currentItem = binding.viewpagerSlider.currentItem
            val nextItem = if (currentItem == itemCount - 1) 0 else currentItem + 1
            binding.viewpagerSlider.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 3000) // 3000 milliseconds = 3 seconds
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managementCart = ManagementCart(requireContext())
        binding.numberInCart.text = managementCart.getListCart().size.toString()

        initBanner()
        initBrand()
        initProduct()
        settingUpListeners()
    }

    override fun onResume() {
        super.onResume()
        binding.numberInCart.text = managementCart.getListCart().size.toString()

    }

    private fun settingUpListeners() {
        binding.cart.setOnClickListener(View.OnClickListener {

            activity?.startActivity(Intent(context, CartActivity::class.java))


        })

    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(viewLifecycleOwner, Observer {
            banner(it)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanner()
    }
    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(viewLifecycleOwner, Observer {
            brand(it)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrand()
    }
    private fun initProduct() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.products.observe(viewLifecycleOwner, Observer {
            product(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadProduct()
    }

    private fun banner(sliderModels : List<SliderModel>) {
        sliderAdapter = SliderAdapter(sliderModels, binding.viewpagerSlider)
        binding.viewpagerSlider.adapter = sliderAdapter
        binding.viewpagerSlider.clipToPadding = false
        binding.viewpagerSlider.clipChildren = false
        binding.viewpagerSlider.offscreenPageLimit = 3
        binding.viewpagerSlider.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val compositePageTranformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewpagerSlider.setPageTransformer(compositePageTranformer)
        if (sliderModels.isNotEmpty()) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewpagerSlider)
        }
        handler.postDelayed(runnable, 3000) // 3000 milliseconds = 3 seconds
    }

    private fun brand(brand: MutableList<BrandModel>){
        brands = brand
        brandAdapter = BrandAdapter(brands)
        binding.recyclerViewBrand.adapter = brandAdapter
        binding.recyclerViewBrand.setHasFixedSize(true)
        binding.recyclerViewBrand.isNestedScrollingEnabled = false
        binding.recyclerViewBrand.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewBrand.adapter = brandAdapter
        binding.recyclerViewBrand.setHasFixedSize(true)
        binding.recyclerViewBrand.isNestedScrollingEnabled = false
        binding.recyclerViewBrand.setItemViewCacheSize(20)

    }
    private fun product(product: MutableList<ProductModel>){
        products = product
        productAdapter = ProductAdapter(products)
        binding.recyclerViewPopular.adapter = productAdapter
        binding.recyclerViewPopular.isNestedScrollingEnabled = true
        binding.recyclerViewPopular.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
        binding.recyclerViewPopular.layoutManager = GridLayoutManager(context, 2)


    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Ngừng tự động chuyển động khi Activity bị phá hủy
    }
}