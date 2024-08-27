package com.quang.lilyshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.Model.ReviewModel
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.SingleProductRatingBinding
import com.quang.lilyshop.databinding.SingleProductReviewBinding
import com.quang.lilyshop.databinding.SingleReviewHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewProductAdapter(val reviews: MutableList<ReviewModel>) :
    RecyclerView.Adapter<ReviewProductAdapter.ViewHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleProductRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (reviews[position].picUrl !="") {
            Glide.with(context)
                .load(reviews[position].picUrl)
                .centerInside()
                .into(holder.binding.image)
        }
        else {
            holder.binding.image.setImageResource(R.drawable.user_default)
        }


        holder.binding.ratingBar2.rating = reviews[position].rating
        holder.binding.name.text = reviews[position].userName
        holder.binding.comment.text = reviews[position].comment
        holder.binding.rating.text = "${reviews[position].rating}"
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        holder.binding.timestamp.text = sdf.format(Date(reviews[position].timestamp))


    }


    override fun getItemCount(): Int = reviews.size

    class ViewHolder(val binding: SingleProductRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}
