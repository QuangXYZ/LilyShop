package com.quang.lilyshop.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.OrderStatus
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.activity.Fragment.AddReviewFragment
import com.quang.lilyshop.activity.Fragment.AllOrderFragment
import com.quang.lilyshop.activity.Fragment.CancelledOrderFragment
import com.quang.lilyshop.activity.Fragment.DeliveredOrderFragment
import com.quang.lilyshop.activity.Fragment.HistoryReviewFragment
import com.quang.lilyshop.activity.Fragment.PendingOrderFragment
import com.quang.lilyshop.activity.Fragment.ProcessingOrderFragment
import com.quang.lilyshop.activity.Fragment.ShippedOrderFragment

class ReviewViewPagerAdapter(fragmentActivity: FragmentActivity, product: List<ProductModel>?, history: List<ProductModel>?) :
    FragmentStateAdapter(fragmentActivity) {


    private val fragments: List<Fragment> = listOf(
        AddReviewFragment.newInstance(product as ArrayList<ProductModel>?),
        HistoryReviewFragment.newInstance(history as ArrayList<ProductModel>?),

    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}