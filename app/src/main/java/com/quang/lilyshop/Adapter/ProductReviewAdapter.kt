package com.quang.lilyshop.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.activity.DetailActivity
import com.quang.lilyshop.activity.ProductRatingActivity
import com.quang.lilyshop.databinding.SingleOrderBinding
import com.quang.lilyshop.databinding.SingleProductReviewBinding

class ProductReviewAdapter(val products: MutableList<ProductModel>) : RecyclerView.Adapter<ProductReviewAdapter.ViewHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleProductReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(products[position].picUrl[0])
            .centerInside()
            .into(holder.binding.productImg)

        holder.binding.productName.text = products[position].title
        holder.binding.productPrice.text = "$${products[position].price}"

        holder.binding.root.setOnClickListener{
            val intent = Intent(context, ProductRatingActivity::class.java)
            intent.putExtra("product", products[position])
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = products.size

    class ViewHolder(val binding: SingleProductReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}



