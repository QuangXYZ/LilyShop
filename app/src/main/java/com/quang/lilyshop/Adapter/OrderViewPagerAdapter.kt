package com.quang.lilyshop.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.OrderStatus
import com.quang.lilyshop.activity.Fragment.CancelledOrderFragment
import com.quang.lilyshop.activity.Fragment.ShippedOrderFragment
import com.quang.lilyshop.activity.Fragment.AllOrderFragment
import com.quang.lilyshop.activity.Fragment.DeliveredOrderFragment
import com.quang.lilyshop.activity.Fragment.PendingOrderFragment
import com.quang.lilyshop.activity.Fragment.ProcessingOrderFragment

class OrderViewPagerAdapter(fragmentActivity: FragmentActivity, orders: List<OrderModel>?) :
    FragmentStateAdapter(fragmentActivity) {

    private val allOrders = orders ?: listOf()
    private val pendingOrders = allOrders.filter { it.status == OrderStatus.PENDING }
    private val processingOrders = allOrders.filter { it.status == OrderStatus.PROCESSING }
    private val shippedOrders = allOrders.filter { it.status == OrderStatus.SHIPPED }
    private val deliveredOrders = allOrders.filter { it.status == OrderStatus.DELIVERED }
    private val cancelledOrders = allOrders.filter { it.status == OrderStatus.CANCELLED }

    private val fragments: List<Fragment> = listOf(
        AllOrderFragment.newInstance(allOrders as ArrayList<OrderModel>?),
        PendingOrderFragment.newInstance(pendingOrders as ArrayList<OrderModel>?),
        ProcessingOrderFragment.newInstance(processingOrders as ArrayList<OrderModel>?),
        ShippedOrderFragment.newInstance(shippedOrders as ArrayList<OrderModel>?),
        DeliveredOrderFragment.newInstance(deliveredOrders as ArrayList<OrderModel>?),
        CancelledOrderFragment.newInstance(cancelledOrders as ArrayList<OrderModel>?),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}