package com.quang.lilyshop.Adapter

import android.content.Context
import android.content.Intent
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
import com.quang.lilyshop.Model.OrderStatus
import com.quang.lilyshop.R
import com.quang.lilyshop.activity.DetailActivity
import com.quang.lilyshop.activity.OrderDetailActivity
import com.quang.lilyshop.databinding.SingleCartItemBinding
import com.quang.lilyshop.databinding.SingleOrderBinding

class OrderAdapter(var orders:MutableList<OrderModel>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private lateinit var context: Context
    private val reversedOrders: List<OrderModel> = orders.reversed()



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
        val order = reversedOrders[position]
        holder.binding.idOrder.text = "ORDER #${order.id.takeLast(6)}"
        var cntItem = 0
        for (product in order.products) {
            cntItem += product.numberInCart
        }
        holder.binding.itemCount.text = "$cntItem items"
        holder.binding.orderTotal.text = "$${order.totalPrice}$"
        holder.binding.status.text = order.status.toString()
        when (order.status.toString()) {
            OrderStatus.PENDING.toString() ->  holder.binding.orderStatus.text = "Order placed, awaiting processing."
            OrderStatus.PROCESSING.toString() ->  holder.binding.orderStatus.text = "Order is being prepared."
            OrderStatus.SHIPPED.toString() ->  holder.binding.orderStatus.text = "Order has been dispatched."
            OrderStatus.DELIVERED.toString() ->  holder.binding.orderStatus.text = "Order delivered to customer."
            OrderStatus.CANCELLED.toString() ->  holder.binding.orderStatus.text = "Order has been cancelled."




        }

        holder.binding.product.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.binding.product.adapter = OrderDetailAdapter(order.products as MutableList)
        holder.binding.root.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, OrderDetailActivity::class.java)
            intent.putExtra("Order", order.id)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return reversedOrders.size
    }

    class OrderViewHolder(val binding: SingleOrderBinding) : RecyclerView.ViewHolder(binding.root) {


    }


}