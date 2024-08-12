package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CancelledFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CancelledFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cancelled, container, false)
    }

    companion object {
        private const val ARG_DATA = "orders"

        fun newInstance(orders: ArrayList<OrderModel>?): CancelledFragment {
            val fragment = CancelledFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList(ARG_DATA, orders)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}