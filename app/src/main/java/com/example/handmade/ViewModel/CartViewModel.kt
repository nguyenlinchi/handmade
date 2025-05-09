package com.example.handmade.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handmade.Model.Cart
import com.example.handmade.Model.CartResponse
import com.example.handmade.Repository.CartRepository
import com.example.handmade.Rest.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {

    private val repository = CartRepository()

    private val _cartItems = MutableLiveData<List<Cart>>()
    val cartItems: LiveData<List<Cart>> = _cartItems

    private val _addToCartSuccess = MutableLiveData<Boolean>()
    val addToCartSuccess: LiveData<Boolean> = _addToCartSuccess

    val updateResult = MutableLiveData<Boolean>()

    // Thêm sản phẩm vào giỏ hàng
    fun addToCart(
        userId: Int,
        productId: Int,
        productName: String,
        productImage: String,
        productPrice: Double,
        selectedSize: String,
        quantity: Int
    ) {
        val cart = Cart(
            user_id = userId,
            product_id = productId,
            product_name = productName,
            product_image = productImage,
            product_price = productPrice,
            size = selectedSize,
            quantity = quantity
        )

        repository.addToCart(cart) { success ->
            _addToCartSuccess.postValue(success)
        }
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    fun fetchCart(userId: Int) {
        RetrofitClient.api.getCart(userId).enqueue(object : Callback<List<Cart>> {
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                if (response.isSuccessful) {
                    _cartItems.postValue(response.body() ?: emptyList())
                } else {
                    _cartItems.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                _cartItems.postValue(emptyList())
            }
        })
    }

    fun updateCartQuantity(userId: Int, productId: Int, quantity: Int) {
        viewModelScope.launch {
            val result = repository.updateQuantity(userId, productId, quantity)
            updateResult.postValue(result)
        }
    }
    fun clearCart(userId: Int) {
        // Gọi API hoặc xử lý xóa sản phẩm trong giỏ hàng của người dùng
        // Sau khi xóa, cập nhật lại danh sách giỏ hàng
        _cartItems.value = emptyList() // Giả sử giỏ hàng đã được xóa
    }
}
