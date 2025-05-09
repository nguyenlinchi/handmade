package com.example.handmade.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handmade.R

class ProductSizeAdapter(
    private val sizes: List<String>,
    private val onSizeSelected: (String) -> Unit
) : RecyclerView.Adapter<ProductSizeAdapter.SizeViewHolder>() {

    private var selectedPosition = -1

    inner class SizeViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(size: String, position: Int) {
            textView.text = size
            textView.setBackgroundResource(
                if (selectedPosition == position)
                    R.drawable.size_selected_background // chọn
                else
                    R.drawable.size_background // chưa chọn
            )

            textView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
                onSizeSelected(size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_size, parent, false) as TextView
        return SizeViewHolder(textView)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(sizes[position], position)
    }

    override fun getItemCount() = sizes.size
}

