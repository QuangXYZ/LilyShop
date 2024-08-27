package com.quang.lilyshop.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.activity.ProductRatingActivity
import com.quang.lilyshop.databinding.SingleProductReviewBinding
import com.quang.lilyshop.databinding.SingleReviewHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewHistoryAdapter (val products: MutableList<ProductModel>, val reviews: MutableList<ReviewModel>) : RecyclerView.Adapter<ReviewHistoryAdapter.ViewHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleReviewHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(products[position].picUrl[0])
            .centerInside()
            .into(holder.binding.productImg)

        holder.binding.productName.text = products[position].title
        holder.binding.productPrice.text = "$${products[position].price}"

//        holder.binding.root.setOnClickListener{
//            val intent = Intent(context, ProductRatingActivity::class.java)
//            intent.putExtra("product", products[position])
//            context.startActivity(intent)
//        }
        holder.binding.name.text = reviews[position].userName
        Glide.with(context)
            .load(products[position].picUrl[0])
            .centerInside()
            .into(holder.binding.circleImageView)
        holder.binding.rating.text = reviews[position].rating.toString()
        holder.binding.comment.text = reviews[position].comment
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        holder.binding.timestamp.text = sdf.format(Date(reviews[position].timestamp))


    }


    override fun getItemCount(): Int = products.size

    class ViewHolder(val binding: SingleReviewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}
