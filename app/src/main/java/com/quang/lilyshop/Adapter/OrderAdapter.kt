package com.quang.lilyshop.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.BrandModel
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.SingleCartItemBinding
import com.quang.lilyshop.databinding.SingleOrderBinding

class OrderAdapter(var orders:MutableList<OrderModel>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        context = parent.context
        val binding =
            SingleOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
        }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.idOrder.text = "ORDER #${order.id.takeLast(6)}"
        holder.binding.itemCount.text = "${order.products.size} items"
        holder.binding.orderTotal.text = "$${order.totalPrice}$"
        holder.binding.orderStatus.text = order.status.toString()

        holder.binding.product.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.binding.product.adapter = OrderDetailAdapter(order.products as MutableList)
        holder.binding.root.setOnClickListener(View.OnClickListener {

        })
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder(val binding: SingleOrderBinding) : RecyclerView.ViewHolder(binding.root) {


    }


}