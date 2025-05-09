package com.example.handmade.Activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.handmade.Model.OrderItem
import com.example.handmade.Model.OrderRequest
import com.example.handmade.ViewModel.CartViewModel
import com.example.handmade.ViewModel.OrderViewModel
import com.example.handmade.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private val cartViewModel: CartViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy user_id từ SharedPreferences
        val sharedPref = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)

        // Thiết lập Spinner phương thức thanh toán
        val paymentMethods = listOf("COD", "Bank Transfer", "Credit Card")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPayment.adapter = spinnerAdapter

        // Tính tổng tiền
        cartViewModel.cartItems.observe(this) { cartItems ->
            val subtotal = cartItems.sumOf { it.product_price * it.quantity }
            binding.txtTotalAmount.text = "%.2f".format(subtotal)

            // Gán sự kiện đặt hàng
            binding.btnPlaceOrder.setOnClickListener {
                val address = binding.edtAddress.text.toString()
                val phone = binding.edtPhone.text.toString()
                val note = binding.edtNote.text.toString()
                val payment = binding.spinnerPayment.selectedItem.toString()
                val totalAmount = subtotal

                // Kiểm tra dữ liệu
                if (address.isBlank() || phone.isBlank()) {
                    Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Tạo danh sách sản phẩm đặt hàng
                val orderItems = cartItems.map {
                    OrderItem(
                        product_id = it.product_id,
                        quantity = it.quantity,
                        price = it.product_price,
                        total = it.product_price * it.quantity
                    )
                }

                val orderRequest = OrderRequest(
                    user_id = userId,
                    delivery_address = address,
                    phone_number = phone,
                    total_amount = totalAmount,
                    payment_method = payment,
                    note = note,
                    order_items = orderItems
                )

                // Gửi đơn hàng
                orderViewModel.placeOrder(orderRequest)
            }
        }

        // Xử lý kết quả trả về
        orderViewModel.orderResult.observe(this) { result ->
            if (result.success) {
                Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show()

                // Xóa sản phẩm trong giỏ hàng sau khi đặt hàng thành công
                cartViewModel.clearCart(userId)

                finish() // Quay lại màn hình trước
            } else {
                Toast.makeText(this, "Thất bại: ${result.message}", Toast.LENGTH_LONG).show()
            }
        }

        // Gọi lại giỏ hàng nếu cần
        if (userId != -1) {
            cartViewModel.fetchCart(userId)
        }
    }
}
