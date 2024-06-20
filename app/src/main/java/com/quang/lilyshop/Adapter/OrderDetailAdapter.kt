package com.quang.lilyshop.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.activity.DetailActivity
import com.quang.lilyshop.databinding.SingleOrderDetailBinding

class OrderDetailAdapter(val products: MutableList<ProductModel>) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    private lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = SingleOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(products[position].picUrl[0])
            .centerInside()
            .into(holder.binding.imageView)

        holder.binding.title.text = products[position].title
        holder.binding.price.text = "$${products[position].price}"
        holder.binding.size.text = "$${products[position].selectedSize}"
        holder.binding.numberInCart.text = "X${products[position].numberInCart}"

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", products[position])
            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = products.size

    class ViewHolder(val binding: SingleOrderDetailBinding) : RecyclerView.ViewHolder(binding.root){

    }


}