package com.quang.lilyshop.Adapter

import android.content.Context
import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.databinding.SingleProvinceBinding
import com.quang.lilyshop.databinding.SingleSearchRecentBinding

class RecentSearchAdapter (val history: MutableList<String>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {

    private lateinit var context: Context




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleSearchRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = history[position]
    }


    override fun getItemCount(): Int = history.size

    class ViewHolder(val binding: SingleSearchRecentBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private val itemClickListener = listener

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(absoluteAdapterPosition)
        }
    }

}