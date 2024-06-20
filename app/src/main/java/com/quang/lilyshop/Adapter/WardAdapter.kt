package com.quang.lilyshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.Model.ProvinceModel
import com.quang.lilyshop.Model.WardModel
import com.quang.lilyshop.databinding.SingleProvinceBinding
import java.util.Locale

class WardAdapter(val wards: List<WardModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<WardAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var filteredList = ArrayList<WardModel>()

    init {
        filteredList.addAll(wards)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleProvinceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = filteredList[position].ward_name


    }


    override fun getItemCount(): Int = filteredList.size

    class ViewHolder(val binding: SingleProvinceBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private val itemClickListener = listener

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(absoluteAdapterPosition)
        }
    }

    fun filter(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(wards)
        } else {
            val lowerCaseQuery = query.lowercase(Locale.ROOT)
                wards.forEach {
                if (it.ward_name.lowercase(Locale.ROOT).contains(lowerCaseQuery)) {
                    filteredList.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }
}


