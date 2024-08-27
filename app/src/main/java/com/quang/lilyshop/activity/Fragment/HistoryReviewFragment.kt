package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.ProductReviewAdapter
import com.quang.lilyshop.Adapter.ReviewHistoryAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.HistoryReviewViewModel
import com.quang.lilyshop.databinding.FragmentHistoryReviewBinding


class HistoryReviewFragment : Fragment() {
    private lateinit var binding: FragmentHistoryReviewBinding
    private lateinit var viewModel: HistoryReviewViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val ARG_DATA = "product"


        fun newInstance(products: ArrayList<ProductModel>?): HistoryReviewFragment {
            val fragment = HistoryReviewFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList(ARG_DATA, products)
            }
            fragment.arguments = bundle
            return fragment
        }

    }

    private fun init() {
        viewModel = HistoryReviewViewModel()
        val products: ArrayList<ProductModel>? = arguments?.getParcelableArrayList(ARG_DATA)



        viewModel.getReviewByProduct(products as ArrayList<ProductModel>, {
            binding.productList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.productList.adapter =
                ReviewHistoryAdapter(
                    products,
                    it as ArrayList<ReviewModel>
                )


        }, {

        })
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
}