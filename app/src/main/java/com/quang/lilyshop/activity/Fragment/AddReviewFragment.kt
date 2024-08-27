package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.ProductReviewAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.FragmentAddReviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddReviewFragment : Fragment() {
    private lateinit var binding: FragmentAddReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        private const val ARG_DATA = "product"

        fun newInstance(product: ArrayList<ProductModel>?): AddReviewFragment {
            val fragment = AddReviewFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList(ARG_DATA, product)
            }
            fragment.arguments = bundle
            return fragment
        }

    }


    private fun init() {
        val products: ArrayList<ProductModel>? = arguments?.getParcelableArrayList(ARG_DATA)

            binding.productList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.productList.adapter =
                ProductReviewAdapter(products as MutableList<ProductModel>)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }
}