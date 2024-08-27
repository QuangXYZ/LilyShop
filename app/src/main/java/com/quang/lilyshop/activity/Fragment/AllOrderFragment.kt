package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.OrderAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.databinding.FragmentToPayBinding

class AllOrderFragment : Fragment() {
    private lateinit var binding: FragmentToPayBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToPayBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root  }
    companion object {
        private const val ARG_DATA = "orders"

        fun newInstance(orders: ArrayList<OrderModel>?): AllOrderFragment {
            val fragment = AllOrderFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList(ARG_DATA, orders)
            }
            fragment.arguments = bundle
            return fragment
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orders: ArrayList<OrderModel>? = arguments?.getParcelableArrayList(ARG_DATA)
        binding.ordersList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.ordersList.adapter = orders?.let { OrderAdapter(it) }



    }
}