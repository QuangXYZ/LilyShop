package com.quang.lilyshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.SingleAddressBinding

class AddressAdapter(val address: MutableList<AddressModel>) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var selectedPosition = 0
    private var lastSelectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.label.text = address[position].label
        holder.binding.name.text = "${address[position].fullName}"
        holder.binding.phone.text = "(+84) ${address[position].phoneNumber}"
        holder.binding.houseNo.text = address[position].address


        holder.binding.city.text =
            "${address[position].ward}, ${address[position].district}, ${address[position].city}"

        if (selectedPosition == holder.absoluteAdapterPosition) {
            holder.binding.radioBtn.isChecked = true
            holder.binding.layout.strokeColor = context.resources.getColor(R.color.purple)
        } else {
            holder.binding.radioBtn.isChecked = false
            holder.binding.layout.strokeColor = context.resources.getColor(R.color.lightGrey)
        }

        if (address[position].default) {
            holder.binding.defaultAddress.visibility = View.VISIBLE

        } else holder.binding.defaultAddress.visibility = View.INVISIBLE

        holder.binding.radioBtn.setOnClickListener {
            selectedPosition = holder.absoluteAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            lastSelectedPosition = selectedPosition
        }
        holder.binding.layout.setOnClickListener {
            selectedPosition = holder.absoluteAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            lastSelectedPosition = selectedPosition

        }

    }


    override fun getItemCount(): Int = address.size

    class ViewHolder(val binding: SingleAddressBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}