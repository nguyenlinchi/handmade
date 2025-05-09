package com.example.handmade.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handmade.Model.Cart
import com.example.handmade.R
import com.example.handmade.ViewModel.CartViewModel

class CartAdapter(
    private var cartItems: List<Cart>,
    private val viewModel: CartViewModel,
    private val context: Context,
    private val userId: Int,
    private val onTotalChanged: (subtotal: Double, delivery: Double, tax: Double, total: Double) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.imageView8)
        val productTitle: TextView = itemView.findViewById(R.id.TitleTxt)
        val feeEachItem: TextView = itemView.findViewById(R.id.feeEachItem)
        val totalEachItem: TextView = itemView.findViewById(R.id.totalEachItem)
        val quantityTxt: TextView = itemView.findViewById(R.id.quantityTxt)
        val plusCart: Button = itemView.findViewById(R.id.plusCart)
        val minusCart: Button = itemView.findViewById(R.id.minusCart)
        val textSize: TextView = itemView.findViewById(R.id.textSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.productTitle.text = cartItem.product_name
        holder.textSize.text = cartItem.size
        holder.feeEachItem.text = "$${cartItem.product_price}"
        holder.quantityTxt.text = cartItem.quantity.toString()

        val total = cartItem.product_price * cartItem.quantity
        holder.totalEachItem.text = "$${"%.2f".format(total)}"

        Glide.with(context)
            .load(cartItem.product_image)
            .into(holder.productImage)

        holder.plusCart.setOnClickListener {
            val newQuantity = cartItem.quantity + 1
            if (userId != -1) {
                viewModel.updateCartQuantity(cartItem.product_id, newQuantity, userId)
                cartItem.quantity = newQuantity
                notifyItemChanged(position)
                calculateTotals()
            }
        }

        holder.minusCart.setOnClickListener {
            if (cartItem.quantity > 1 && userId != -1) {
                val newQuantity = cartItem.quantity - 1
                viewModel.updateCartQuantity(cartItem.product_id, newQuantity, userId)
                cartItem.quantity = newQuantity
                notifyItemChanged(position)
                calculateTotals()
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newCartItems: List<Cart>) {
        cartItems = newCartItems
        notifyDataSetChanged()
        calculateTotals()
    }

    private fun calculateTotals() {
        var subtotal = 0.0
        for (item in cartItems) {
            subtotal += item.product_price * item.quantity
        }
        val delivery = 5.0
        val tax = 10.0
        val total = subtotal + delivery + tax
        onTotalChanged(subtotal, delivery, tax, total)
    }

    fun getCurrentCartItems(): List<Cart> {
        return cartItems
    }
}
