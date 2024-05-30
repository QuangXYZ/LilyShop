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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.BrandModel
import com.quang.lilyshop.R

class BrandAdapter(var brands:MutableList<BrandModel>):RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandAdapter.BrandViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_brand_item, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandAdapter.BrandViewHolder, position: Int) {
        holder.setBrand(brands[position],context)
        holder.layout.setOnClickListener(View.OnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.absoluteAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        })
        if (selectedPosition == holder.absoluteAdapterPosition) {
            holder.layout.setBackgroundResource(R.drawable.purple_background)
            holder.title.setTextColor(context.resources.getColor(R.color.white))
            holder.title.visibility = View.VISIBLE
            ImageViewCompat.setImageTintList(holder.image, ColorStateList.valueOf(context.resources.getColor(R.color.white)))
        } else {
            holder.layout.setBackgroundResource(R.drawable.grey_background)
            holder.title.setTextColor(context.resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(holder.image, ColorStateList.valueOf(context.resources.getColor(R.color.black)))
            holder.title.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return brands.size
    }

    class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.brandTitle)
        val image:ImageView = itemView.findViewById(R.id.brandImage)
        val layout:LinearLayout = itemView.findViewById(R.id.brandLayout)

        fun setBrand(brandModel: BrandModel, context: Context) {
            title.text = brandModel.title
            Glide.with(context)
                .load(brandModel.picUrl)
                .centerInside()
                .into(image)
        }

    }

}