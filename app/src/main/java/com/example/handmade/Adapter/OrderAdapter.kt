package com.example.handmade.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handmade.Model.OrderItem
import com.example.handmade.databinding.ItemOrderProductBinding

class OrderAdapter(private var orderItems: List<OrderItem>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderItem) {
            binding.txtProductName.text = "Sản phẩm ID: ${item.product_id}"
            binding.txtQuantity.text = "Số lượng: ${item.quantity}"
            binding.txtUnitPrice.text = "Đơn giá: ${item.price}đ"
            binding.txtTotal.text = "Tổng: ${item.total}đ"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount(): Int = orderItems.size

    fun setData(newList: List<OrderItem>) {
        orderItems = newList
        notifyDataSetChanged()
    }
}
