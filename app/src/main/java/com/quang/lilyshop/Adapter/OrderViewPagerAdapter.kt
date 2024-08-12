package com.quang.lilyshop.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.activity.Fragment.CancelledFragment
import com.quang.lilyshop.activity.Fragment.CompletedFragment
import com.quang.lilyshop.activity.Fragment.ToPayFragment
import com.quang.lilyshop.activity.Fragment.ToShipFragment

class OrderViewPagerAdapter(fragmentActivity: FragmentActivity, orders:List<OrderModel>?) : FragmentStateAdapter(fragmentActivity) {
    private val fragments: List<Fragment> = listOf(
        ToPayFragment.newInstance(orders as ArrayList<OrderModel>?),
        ToShipFragment.newInstance(orders),
        CompletedFragment.newInstance(orders),
        CancelledFragment.newInstance(orders)
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}