package com.quang.lilyshop.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.OrderDetailAdapter
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.OrderStatus
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.OrderDetailViewModel
import com.quang.lilyshop.databinding.ActivityOrderDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var order : OrderModel
    private lateinit var viewModel: OrderDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingUpListener()
    }

    private fun init () {
        viewModel = OrderDetailViewModel()
        val idOrder = intent.getStringExtra("Order")!!
        viewModel.getOrderById(idOrder)

        viewModel.orders.observe(this) {
            order = it
            when (order.status) {
                OrderStatus.PENDING -> {
                    binding.orderStatus.text = "Order Pending"
                    binding.shippedTimeLayout.visibility = View.GONE
                    binding.deliveryTimeLayout.visibility = View.GONE
                    binding.orderStatusIcon.setImageDrawable(getDrawable(R.drawable.pending))
                    binding.orderStatusDetail.text = "Order placed, awaiting processing."
                    binding.cancelOrder.visibility = View.VISIBLE
                }

                OrderStatus.PROCESSING -> {
                    binding.orderStatus.text = "Order Processing"
                    binding.shippedTimeLayout.visibility = View.GONE
                    binding.deliveryTimeLayout.visibility = View.GONE
                    binding.orderStatusIcon.setImageDrawable(getDrawable(R.drawable.processing))
                    binding.orderStatusDetail.text = "Order is being prepared.."
                    binding.cancelOrder.visibility = View.VISIBLE
                }

                OrderStatus.SHIPPED -> {
                    binding.orderStatus.text = "Order Shipped"
                    binding.deliveryTime.visibility = View.GONE
                    binding.orderStatusIcon.setImageDrawable(getDrawable(R.drawable.shipped))
                    binding.orderStatusDetail.text = "Order has been dispatched."
                    binding.cancelOrder.visibility = View.GONE


                }

                OrderStatus.DELIVERED -> {
                    binding.orderStatus.text = "Order Delivered"
                    binding.orderStatusIcon.setImageDrawable(getDrawable(R.drawable.delivery))
                    binding.shippedTimeLayout.visibility = View.GONE
                    binding.deliveryTimeLayout.visibility = View.GONE
                    binding.orderStatusDetail.text = "Order delivered to customer."
                    binding.cancelOrder.visibility = View.GONE


                }

                OrderStatus.CANCELLED -> {
                    binding.orderStatus.text = "Order Cancelled"
                    binding.orderStatusDetail.text = "Order has been cancelled."
                    binding.orderStatusIcon.setImageDrawable(getDrawable(R.drawable.cancel))

                    binding.cancelOrder.visibility = View.GONE

                    binding.statusLayout.background = getDrawable(R.color.orange)

                }

                else -> {}
            }


            binding.idOrder.text = "#${order.id.takeLast(6)}"

            binding.orderTime.text = formatTimestamp(order.timestamp)

            binding.viewOrder.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.viewOrder.adapter =
                OrderDetailAdapter(order.products as MutableList<ProductModel>)


            var total = 0.0
            order.products.forEach {
                total += it.price * it.numberInCart
            }
            with(binding) {
                orderTotal.text = "$${order.totalPrice}"
                totalTax.text = "$${order.tax}"
                feeDelivery.text = "$${order.shippingFee}"
                subtotal.text = "$${total}"
            }

            binding.addressName.text =
                "${order.shippingAddress?.fullName} ${order.shippingAddress?.phoneNumber}"
            binding.addressStreet.text = "${order.shippingAddress?.address}"
            binding.addressProvince.text =
                "${order.shippingAddress?.ward}, ${order.shippingAddress?.district}, ${order.shippingAddress?.city}"


        }


    }
    private fun settingUpListener () {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.cancelOrder.setOnClickListener {
            viewModel.cancelOrder(order.id, {

            })
        }
    }

    fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}