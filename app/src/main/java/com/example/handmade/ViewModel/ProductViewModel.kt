package com.example.handmade.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.handmade.Model.Product
import com.example.handmade.Rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchProducts() {
        // Chỉ cần gọi API getProducts() một lần từ RetrofitClient
        val apiService = RetrofitClient.api // Giả sử đây là đối tượng API đã được cấu hình đúng
        val call = apiService.getProducts() // Gọi API từ RetrofitClient

        // Xử lý response từ API
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful && response.body() != null) {
                    _products.postValue(response.body()) // Cập nhật LiveData với danh sách sản phẩm
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // Xử lý lỗi nếu có
            }
        })
    }

}
