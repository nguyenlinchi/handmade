package com.example.handmade.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handmade.Model.OrderItem
import com.example.handmade.Model.OrderRequest
import com.example.handmade.Model.OrderResponse
import com.example.handmade.Rest.RetrofitClient.api
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class OrderViewModel : ViewModel() {

    // Danh sách sản phẩm trong giỏ hàng hoặc đặt hàng
    private val _orderItemList = MutableLiveData<List<OrderItem>>()
    val orderItemList: LiveData<List<OrderItem>> get() = _orderItemList

    // Kết quả đặt hàng
    private val _orderResult = MutableLiveData<OrderResponse>()
    val orderResult: LiveData<OrderResponse> get() = _orderResult

    // Gán dữ liệu từ giỏ hàng (hoặc nơi khác)
    fun setOrderItems(items: List<OrderItem>) {
        _orderItemList.value = items
    }

    // Gửi yêu cầu đặt hàng
    fun placeOrder(orderRequest: OrderRequest) {
        viewModelScope.launch {
            try {
                val response = api.placeOrder(orderRequest)

                if (response.isSuccessful) {
                    // Trả về OrderResponse khi thành công
                    _orderResult.value = response.body() ?: OrderResponse(false, "Lỗi không xác định")
                } else {
                    // Trường hợp không thành công, trả về thông báo lỗi
                    _orderResult.value = OrderResponse(false, "Lỗi từ server: ${response.code()}")
                }
            } catch (e: IOException) {
                _orderResult.value = OrderResponse(false, "Lỗi kết nối mạng")
            } catch (e: HttpException) {
                _orderResult.value = OrderResponse(false, "Lỗi hệ thống")
            }
        }
    }

}
