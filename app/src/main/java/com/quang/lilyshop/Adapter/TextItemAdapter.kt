package com.quang.lilyshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lriccardo.timelineview.TimelineAdapter
import com.lriccardo.timelineview.TimelineView
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.SingleLineAddressBinding
import com.quang.lilyshop.databinding.SingleTextItemBinding

class TextItemAdapter(
    val list: MutableList<String>,
) : RecyclerView.Adapter<TextItemAdapter.ViewHolder>(){

    private val selectedItems = mutableSetOf<Int>()
    private lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleTextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val label = list[position]
        holder.bind(label, context, selectedItems.contains(position))

        holder.binding.root.setOnClickListener {
            toggleSelection(position)
        }

    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: SingleTextItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(label: String,context: Context, isSelected: Boolean) {
            binding.label.text = label
            binding.root.isSelected = isSelected
            binding.root.strokeColor = if (isSelected) context.getColor(R.color.black) else context.getColor(R.color.white)
        }


    }

    private fun toggleSelection(position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.add(position)
        }
        notifyItemChanged(position)
    }





}