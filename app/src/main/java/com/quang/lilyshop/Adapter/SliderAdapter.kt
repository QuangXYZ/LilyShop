package com.quang.lilyshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.quang.lilyshop.Model.SliderModel
import com.quang.lilyshop.R

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private lateinit var context:Context
    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(view)
    }


    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position],context)
//        if (position == sliderItems.lastIndex - 1) {
//            viewPager2.post(runnable)
//        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    class SliderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val image:ImageView = itemView.findViewById(R.id.imageSlider)

        fun setImage(sliderItems: SliderModel, context:Context){
            Glide.with(context)
                .load(sliderItems.url)
                .centerInside()
                .into(image)
        }
    }

}