package com.example.handmade.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handmade.Model.Product
import com.example.handmade.R

class SearchAdapter(private var items: List<Product>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.productImage)
        val name: TextView = itemView.findViewById(R.id.productName)
        val price: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = items[position]
        holder.name.text = product.name
        holder.price.text = "${product.price}₫"

        // Kiểm tra URL và tải ảnh bằng Glide
        Glide.with(holder.itemView.context)
            .load(product.image_url) // URL ảnh
            .error(R.drawable.image) // Thêm ảnh lỗi nếu không tải được
            .into(holder.image)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }
}
