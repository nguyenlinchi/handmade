package com.example.handmade.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.handmade.Model.ProductDetail
import com.example.handmade.Rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel : ViewModel() {

    // LiveData cho chi tiết sản phẩm
    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail: LiveData<ProductDetail> get() = _productDetail

    // LiveData cho lỗi
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Fetch chi tiết sản phẩm từ API
    fun fetchProductDetail(productId: String) {
        val apiService = RetrofitClient.api // Sử dụng RetrofitClient đã cấu hình
        val call = apiService.getProductDetails(productId) // Gọi API để lấy chi tiết sản phẩm

        // Xử lý response từ API
        call.enqueue(object : Callback<ProductDetail> {
            override fun onResponse(call: Call<ProductDetail>, response: Response<ProductDetail>) {
                if (response.isSuccessful && response.body() != null) {
                    // Cập nhật LiveData với chi tiết sản phẩm nếu phản hồi thành công
                    _productDetail.postValue(response.body())
                } else {
                    // Hiển thị lỗi nếu không tìm thấy sản phẩm
                    _error.postValue("Lỗi: Không tìm thấy sản phẩm hoặc mã lỗi: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                // Hiển thị lỗi nếu có lỗi kết nối
                _error.postValue("Lỗi: ${t.message}")
            }
        })
    }
}
