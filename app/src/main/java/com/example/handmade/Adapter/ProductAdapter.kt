package com.example.handmade.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handmade.Activity.ProductDetailActivity
import com.example.handmade.Model.Product
import com.example.handmade.R
import com.squareup.picasso.Picasso

class ProductAdapter(private val productList: List<Product>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productLeft = productList.getOrNull(position * 2) // Sản phẩm bên trái
        val productRight = productList.getOrNull(position * 2 + 1) // Sản phẩm bên phải

        // Hiển thị sản phẩm bên trái
        productLeft?.let { product ->
            Picasso.get().load(product.image_url).into(holder.imageLeft)
            holder.nameLeft.text = product.name
            holder.priceLeft.text = "$${product.price}"

            // Nhấn vào sản phẩm bên trái để chuyển đến ProductDetailActivity
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT_ID", product.id.toString())
                context.startActivity(intent)
            }

            // Nhấn vào ảnh sản phẩm bên trái
            holder.imageLeft.setOnClickListener {
                navigateToProductDetail(product)
            }
        }

        // Hiển thị sản phẩm bên phải
        productRight?.let { product ->
            Picasso.get().load(product.image_url).into(holder.imageRight)
            holder.nameRight.text = product.name
            holder.priceRight.text = "$${product.price}"

            // Nhấn vào sản phẩm bên phải để chuyển đến ProductDetailActivity
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT_ID", product.id.toString()) // Đảm bảo key này khớp với trong Activity
                context.startActivity(intent)
            }

            // Nhấn vào ảnh sản phẩm bên phải
            holder.imageRight.setOnClickListener {
                navigateToProductDetail(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return (productList.size + 1) / 2 // Mỗi hàng chứa 2 sản phẩm
    }

    private fun navigateToProductDetail(product: Product) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("PRODUCT_ID", product.id.toString()) // Chỉnh sửa key cho đúng
        context.startActivity(intent)
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Left Product Views
        val imageLeft: ImageView = view.findViewById(R.id.product_image_left)
        val nameLeft: TextView = view.findViewById(R.id.product_name_left)
        val priceLeft: TextView = view.findViewById(R.id.product_price_left)

        // Right Product Views
        val imageRight: ImageView = view.findViewById(R.id.product_image_right)
        val nameRight: TextView = view.findViewById(R.id.product_name_right)
        val priceRight: TextView = view.findViewById(R.id.product_price_right)
    }
}

