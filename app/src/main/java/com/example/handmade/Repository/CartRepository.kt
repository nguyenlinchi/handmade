package com.example.handmade.Repository

import android.util.Log
import com.example.handmade.Model.Cart
import com.example.handmade.Model.CartResponse
import com.example.handmade.Rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository {
    private val api = RetrofitClient.api

    // Thêm sản phẩm vào giỏ hàng
    fun addToCart(cart: Cart, callback: (Boolean) -> Unit) {
        api.addToCart(cart).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if (response.isSuccessful) {
                    Log.d("CartRepository", "✅ Thêm vào giỏ hàng thành công")
                    callback(true)
                } else {
                    Log.e("CartRepository", "❌ Lỗi phản hồi khi thêm vào giỏ hàng")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Log.e("CartRepository", "❌ Lỗi mạng khi thêm vào giỏ hàng: ${t.message}")
                callback(false)
            }
        })
    }
    fun getCart(userId: Int, callback: (List<Cart>?) -> Unit) {
        api.getCart(userId).enqueue(object : Callback<List<Cart>> {
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                callback(null)
            }
        })
    }
    // Cập nhật số lượng sản phẩm trong giỏ hàng
    suspend fun updateQuantity(userId: Int, productId: Int, quantity: Int): Boolean {
        return try {
            val response: Response<CartResponse> = api.updateQuantity(userId, productId, quantity)
            if (response.isSuccessful && response.body()?.success == true) {
                Log.d("CartRepository", "✅ Cập nhật số lượng sản phẩm thành công")
                true
            } else {
                Log.e("CartRepository", "❌ Lỗi phản hồi khi cập nhật số lượng")
                false
            }
        } catch (e: Exception) {
            Log.e("CartRepository", "❌ Lỗi khi cập nhật số lượng: ${e.message}")
            false
        }
    }
}
