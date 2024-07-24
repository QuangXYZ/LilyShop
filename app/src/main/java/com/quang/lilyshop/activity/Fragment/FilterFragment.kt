package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.slider.RangeSlider
import com.quang.lilyshop.Adapter.BrandAdapter
import com.quang.lilyshop.Adapter.TextItemAdapter
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.FragmentFilterBinding


class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private lateinit var brandAdapter: TextItemAdapter
    private lateinit var categoryAdapter: TextItemAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initRecyclerView()
    }


    private fun initRecyclerView() {
        brandAdapter = TextItemAdapter(arrayListOf("Brand adasd1", "Branaaadasd 2", "aaBrand 3", "and 4", "Brand a5","Braaand 2", "Brand 3a", "Br 4", "Brand 5"))
        binding.listBrand.layoutManager =  FlexboxLayoutManager(requireContext())
        binding.listBrand.adapter = brandAdapter
        binding.listBrand.isNestedScrollingEnabled = false

        binding.listCategory.layoutManager =  FlexboxLayoutManager(requireContext())
        categoryAdapter = TextItemAdapter(arrayListOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5"))
        binding.listCategory.adapter = categoryAdapter
        binding.listBrand.isNestedScrollingEnabled = false



    }

    private fun init() {
        binding.slider.addOnChangeListener { rangeSlider, value, fromUser ->
            binding.minPrice.text = "${rangeSlider.values[0].toInt()}$"
            binding.maxPrice.text = "${rangeSlider.values[1].toInt()}$"
        }


    }


}