package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.OrderAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.FragmentCompletedBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShippedOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShippedOrderFragment : Fragment() {
    private lateinit var binding: FragmentCompletedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val ARG_DATA = "orders"

        fun newInstance(orders: ArrayList<OrderModel>?): ShippedOrderFragment {
            val fragment = ShippedOrderFragment()
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