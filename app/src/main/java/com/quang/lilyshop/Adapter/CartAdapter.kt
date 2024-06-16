package com.quang.lilyshop.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.lilyshop.Helper.ChangeNumberItemsListener
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.Model.ProductModel
import com.quang.lilyshop.activity.DetailActivity
import com.quang.lilyshop.databinding.SingleCartItemBinding

class CartAdapter(
    private val listItemSelected: ArrayList<ProductModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managementCart = ManagementCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SingleCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listItemSelected.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItemSelected[position]
        holder.binding.productName.text = item.title
        holder.binding.productPrice.text = "${item.price}"
        holder.binding.productTotalPrice.text = "$${Math.round(item.price*item.numberInCart)}"
        holder.binding.numberInCart.text = "${item.numberInCart}"
        holder.binding.productSize.text = "Size : ${item.selectedSize}"
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .centerInside()
            .into(holder.binding.productImg)
        holder.binding.minusCartBtn.setOnClickListener {
            managementCart.minusItem(listItemSelected, position,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }
                })
        }
        holder.binding.plusCartBtn.setOnClickListener {
            managementCart.plusItem(listItemSelected, position,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }
                })
        }
        holder.binding.removeProduct.setOnClickListener {
            managementCart.deleteItem(listItemSelected, position,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }
                })
        }
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", listItemSelected[position])
            holder.itemView.context.startActivity(intent)
        }


    }
    class ViewHolder(val binding: SingleCartItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}