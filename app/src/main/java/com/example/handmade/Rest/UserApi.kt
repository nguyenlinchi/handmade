package com.example.handmade.Rest


//import com.example.handmade.Model.Cart
//import com.example.handmade.Model.CartResponse
import com.example.handmade.Model.Cart
import com.example.handmade.Model.CartResponse
import com.example.handmade.Model.Category
import com.example.handmade.Model.LoginRequest
import com.example.handmade.Model.LoginResponse
import com.example.handmade.Model.OrderRequest
import com.example.handmade.Model.OrderResponse
import com.example.handmade.Model.Product
import com.example.handmade.Model.ProductDetail
import com.example.handmade.Model.RegisterRequest
import com.example.handmade.Model.RegisterResponse
import retrofit2.*
import retrofit2.http.*

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("register.php")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
    @POST("login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
    @GET("get_categories.php") // Đảm bảo URL này trùng với URL bạn dùng trong API
    suspend fun getCategories(): List<Category>

    @GET("get_products.php") // Đảm bảo đường dẫn này chính xác với API của bạn
    fun getProducts(): Call<List<Product>>

    @GET("get_product_details.php")
    fun getProductDetails(@Query("id") productId: String): Call<ProductDetail>

    @Headers("Content-Type: application/json")
    @POST("add_to_cart.php")
    fun addToCart(@Body cart: Cart): Call<CartResponse>

    @GET("get_cart.php")
    fun getCart(@Query("user_id") userId: Int): Call<List<Cart>>

    @FormUrlEncoded
    @POST("update_quantity.php")
    suspend fun updateQuantity(
        @Field("user_id") userId: Int,
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int
    ): Response<CartResponse>

    @GET("search_products.php")
    suspend fun searchProducts(@Query("keyword") keyword: String): List<Product>

    @POST("orders.php")
    suspend fun placeOrder(@Body request: OrderRequest): Response<OrderResponse>
}