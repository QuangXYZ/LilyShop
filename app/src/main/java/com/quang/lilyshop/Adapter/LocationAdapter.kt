package com.quang.lilyshop.Adapter

import android.content.Context
import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.Model.DistrictModel
import com.quang.lilyshop.databinding.SingleProvinceBinding

class LocationAdapter(val address: MutableList<Address>, private val listener: OnItemClickListener) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private lateinit var context: Context

    fun updateAddresses(newAddresses: List<Address>) {
        address.clear()
        address.addAll(newAddresses)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleProvinceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = address[position].getAddressLine(0)


    }


    override fun getItemCount(): Int = address.size

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

}