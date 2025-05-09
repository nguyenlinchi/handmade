package com.example.handmade.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handmade.databinding.FragmentCartBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.handmade.Adapter.CartAdapter
import com.example.handmade.ViewModel.CartViewModel
import com.example.handmade.ViewModel.OrderViewModel
import com.example.handmade.Activity.OrderActivity
import com.example.handmade.Model.OrderItem

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()


    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val context = requireContext()

        val userId = requireActivity()
            .getSharedPreferences("USER_SESSION", AppCompatActivity.MODE_PRIVATE)
            .getInt("USER_ID", -1)

        if (userId != -1) {
            adapter = CartAdapter(
                emptyList(),
                cartViewModel,
                context,
                userId,
                onTotalChanged = { subtotal, delivery, tax, total ->
                    binding.apply {
                        subtotalText.text = "Subtotal: $${"%.2f".format(subtotal)}"
                        deliveryText.text = "Delivery: $${"%.2f".format(delivery)}"
                        taxText.text = "Tax: $${"%.2f".format(tax)}"
                        totalText.text = "Total: $${"%.2f".format(total)}"
                    }
                }
            )

            binding.recyclerCart.layoutManager = LinearLayoutManager(context)
            binding.recyclerCart.adapter = adapter

            cartViewModel.cartItems.observe(viewLifecycleOwner) { list ->
                adapter.updateData(list)
            }

            cartViewModel.fetchCart(userId)

            // ✅ Bổ sung sự kiện nhấn nút Checkout
            binding.btnCheckout.setOnClickListener {
                val cartItems = cartViewModel.cartItems.value ?: emptyList()
                if (cartItems.isEmpty()) return@setOnClickListener

                val orderItems = cartItems.map {
                    OrderItem(
                        product_id = it.product_id,
                        quantity = it.quantity,
                        price = it.product_price,
                        total = it.product_price * it.quantity
                     )
                }

                orderViewModel.setOrderItems(orderItems)

                // Chuyển sang OrderActivity
                val intent = Intent(context, OrderActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }
}
