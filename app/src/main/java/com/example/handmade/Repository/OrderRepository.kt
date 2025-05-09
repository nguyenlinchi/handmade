package com.example.handmade.Repository

import com.example.handmade.Model.OrderRequest
import com.example.handmade.Model.OrderResponse
import com.example.handmade.Rest.RetrofitClient.api
import retrofit2.Response // ✅ đúng

class OrderRepository {
    suspend fun placeOrder(orderRequest: OrderRequest): Response<OrderResponse> {
        return api.placeOrder(orderRequest)
    }
}
