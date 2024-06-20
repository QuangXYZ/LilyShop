package com.quang.lilyshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lriccardo.timelineview.TimelineAdapter
import com.lriccardo.timelineview.TimelineView
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.SingleLineAddressBinding


class AddressLineAdapter(
    val address: MutableList<String>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AddressLineAdapter.ViewHolder>(),
    TimelineAdapter {

    private lateinit var context: Context

    private var selectedPosition = -1 // Vị trí ban đầu không có phần tử được chọn
    private var lastSelectedPosition = -1 // Vị trí của item cuối cùng

    init {
        selectedPosition = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            SingleLineAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lineAddress = address[position]


        holder.binding.textTimelineTitle.text = lineAddress
        if (selectedPosition == holder.absoluteAdapterPosition) {

            holder.binding.layout.strokeColor = context.resources.getColor(R.color.red)
        }
        else {
            holder.binding.layout.strokeColor = context.resources.getColor(R.color.lightGrey)
        }

        holder.itemView.setOnClickListener {
            if (holder.absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                selectedPosition = holder.absoluteAdapterPosition
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
                lastSelectedPosition = selectedPosition

                listener.onItemClick(position) // Gọi callback của Interface khi item được click
            }
        }


    }


    override fun getItemCount(): Int = address.size

    class ViewHolder(val binding: SingleLineAddressBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {





    }

    override fun getIndicatorStyle(position: Int): TimelineView.IndicatorStyle? {
        if (position == address.size - 1)
            return TimelineView.IndicatorStyle.Empty
        else return TimelineView.IndicatorStyle.Checked
    }



    fun select(index: Int) {
        selectedPosition = index
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedPosition)
        lastSelectedPosition = selectedPosition
    }
}