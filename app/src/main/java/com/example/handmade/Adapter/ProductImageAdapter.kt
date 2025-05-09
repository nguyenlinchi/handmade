package com.example.handmade.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handmade.R

class ProductImageAdapter(
    private var images: List<String>,
    private val onImageSelected: (String) -> Unit
) : RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder>() {

    private var selectedPosition = 0

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImageItem)

        init {
            itemView.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                onImageSelected(images[selectedPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_details, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(images[position])
            .into(holder.imageView)

        // Highlight ảnh được chọn
        holder.imageView.alpha = if (position == selectedPosition) 1.0f else 0.5f
    }

    override fun getItemCount(): Int = images.size

    fun updateData(newImages: List<String>) {
        images = newImages
        selectedPosition = 0
        notifyDataSetChanged()
        if (images.isNotEmpty()) {
            onImageSelected(images[0])
        }
    }

    fun getSelectedImage(): String = images.getOrNull(selectedPosition) ?: ""
}
