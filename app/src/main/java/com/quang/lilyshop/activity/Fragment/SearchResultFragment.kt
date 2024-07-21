package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.quang.lilyshop.Adapter.ProductAdapter
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.SearchResultViewModel
import com.quang.lilyshop.databinding.FragmentSearchResultBinding



class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var searchAdapter: ProductAdapter  // Adapter để hiển thị kết quả tìm kiếm
    private lateinit var viewModel: SearchResultViewModel
    private var priceSortState = SortState.NONE
    private lateinit var searchQuery:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        settingUpListener()

    }

    private fun setupRecyclerView() {
        viewModel = SearchResultViewModel()
        binding.searchResult.isNestedScrollingEnabled = true
        binding.searchResult.layoutManager = GridLayoutManager(context, 2)
        viewModel.products.observe(viewLifecycleOwner, Observer {
            searchAdapter = ProductAdapter(it as MutableList<ProductModel>)
            binding.searchResult.adapter = searchAdapter
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }



    private fun settingUpListener() {
        binding.relevance.setOnClickListener {
            updateSelectedButton(binding.relevance)
        }

        binding.latest.setOnClickListener {
            updateSelectedButton(binding.latest)
        }

        binding.topsale.setOnClickListener {
            updateSelectedButton(binding.topsale)
        }

        binding.priceLayout.setOnClickListener {
            binding.relevance.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            binding.latest.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            binding.topsale.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            binding.price.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
            binding.priceLayout.setBackgroundResource(R.drawable.bottom_line_bkg)
            binding.relevance.setBackgroundResource(0)
            binding.latest.setBackgroundResource(0)
            binding.topsale.setBackgroundResource(0)


            priceSortState = when (priceSortState) {
                SortState.NONE -> {

                    viewModel.searchResult(searchQuery, SortState.ASCENDING)
                    SortState.ASCENDING
                }
                SortState.ASCENDING -> {
                    viewModel.searchResult(searchQuery, SortState.DESCENDING)
                    SortState.DESCENDING
                }
                SortState.DESCENDING -> {
                    viewModel.searchResult(searchQuery, SortState.ASCENDING
                    )
                    SortState.ASCENDING
                }
            }

            when (priceSortState) {
                SortState.NONE -> {
//                    binding.price.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
//                    binding.iconPrice.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unfold))

                }
                SortState.ASCENDING -> {
                    binding.price.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
                    binding.iconPrice.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.up_arrow))


                }
                SortState.DESCENDING -> {
                    binding.price.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
                    binding.iconPrice.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.down_arrow))

                }
            }
        }
    }

    private fun updateSelectedButton(selectedButton: TextView) {
        binding.relevance.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        binding.latest.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        binding.topsale.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        binding.price.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))

        binding.relevance.setBackgroundResource(0)
        binding.latest.setBackgroundResource(0)
        binding.topsale.setBackgroundResource(0)
        binding.priceLayout.setBackgroundResource(0)
        binding.iconPrice.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unfold))
        priceSortState = SortState.NONE

        selectedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
        selectedButton.setBackgroundResource(R.drawable.bottom_line_bkg)
    }

    fun search(query: String) {
        searchQuery = query
        viewModel.searchResult(query, SortState.NONE)

    }

    enum class SortState {
        NONE, ASCENDING, DESCENDING
    }


}