package com.example.handmade.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handmade.Adapter.ProductImageAdapter
import com.example.handmade.Adapter.ProductSizeAdapter
import com.example.handmade.MainActivity
import com.example.handmade.R
import com.example.handmade.ViewModel.CartViewModel
import com.example.handmade.ViewModel.ProductDetailViewModel
import com.example.handmade.fragment.CartFragment

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var cartViewModel: CartViewModel

    private lateinit var backBtn: ImageView
    private lateinit var favIcon: ImageView
    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productPrice: TextView
    private lateinit var productBrandImage: ImageView
    private lateinit var productBrand: TextView
    private lateinit var productDescription: TextView
    private lateinit var relatedImagesRecyclerView: RecyclerView
    private lateinit var sizeRecyclerView: RecyclerView
    private lateinit var addToCartButton: Button
    private lateinit var btnCart: ImageView

    private lateinit var productImageAdapter: ProductImageAdapter
    private lateinit var productSizeAdapter: ProductSizeAdapter
    private var selectedSize: String? = null
    private var selectedImageUrl: String = "" // ảnh được chọn từ danh sách

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Ánh xạ View
        backBtn = findViewById(R.id.backBtn)
        favIcon = findViewById(R.id.favIcon)
        productImage = findViewById(R.id.product_image)
        productName = findViewById(R.id.product_name)
        productPrice = findViewById(R.id.product_price)
        productBrandImage = findViewById(R.id.product_brand_image)
        productBrand = findViewById(R.id.product_brand)
        productDescription = findViewById(R.id.product_description)
        relatedImagesRecyclerView = findViewById(R.id.relatedProductsRecyclerView)
        sizeRecyclerView = findViewById(R.id.recyclerViewSizes)
        addToCartButton = findViewById(R.id.button)
        btnCart = findViewById(R.id.btnCart)


        // ViewModels
        productDetailViewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        // Lấy userId từ SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
        userId = sharedPreferences.getInt("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, "Lỗi: Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Khởi tạo RecyclerView hình ảnh phụ
        relatedImagesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productImageAdapter = ProductImageAdapter(emptyList()) { selectedImage ->
            selectedImageUrl = selectedImage
            Glide.with(this)
                .load(selectedImage)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .into(productImage)
        }
        relatedImagesRecyclerView.adapter = productImageAdapter

        // RecyclerView size
        sizeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Quan sát dữ liệu sản phẩm
        productDetailViewModel.productDetail.observe(this) { product ->
            product?.let {
                productName.text = it.name
                productPrice.text = "$${it.price}"
                productDescription.text = it.description
                productBrand.text = it.brand_name ?: "Không rõ thương hiệu"

                // Ảnh chính
                if (it.images.isNotEmpty()) {
                    selectedImageUrl = it.images[0]
                    Glide.with(this)
                        .load(it.images[0])
                        .placeholder(R.drawable.image)
                        .error(R.drawable.image)
                        .into(productImage)
                } else {
                    productImage.setImageResource(R.drawable.image)
                }

                // Ảnh thương hiệu
                if (!it.brand_image.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(it.brand_image)
                        .placeholder(R.drawable.image)
                        .error(R.drawable.image)
                        .into(productBrandImage)
                } else {
                    productBrandImage.setImageResource(R.drawable.image)
                }

                // Ảnh phụ
                productImageAdapter.updateData(it.images)

                // Size
                if (it.sizes.isNotEmpty()) {
                    productSizeAdapter = ProductSizeAdapter(it.sizes) { selected ->
                        selectedSize = selected
                    }
                    sizeRecyclerView.adapter = productSizeAdapter
                } else {
                    Toast.makeText(this, "Chưa có thông tin size", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Quan sát lỗi
        productDetailViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        backBtn.setOnClickListener { finish() }

        // Thêm vào giỏ
        addToCartButton.setOnClickListener {
            if (selectedSize == null) {
                Toast.makeText(this, "Vui lòng chọn size trước khi thêm vào giỏ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = productDetailViewModel.productDetail.value
            if (product != null) {
                try {
                    val productIdInt = product.id.toInt()
                    val productPriceDouble = product.price.toDouble()

                    cartViewModel.addToCart(
                        userId = userId,
                        productId = productIdInt,
                        productName = product.name,
                        productImage = selectedImageUrl,
                        productPrice = productPriceDouble,
                        selectedSize = selectedSize!!,
                        quantity = 1
                    )

                    Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Toast.makeText(this, "Lỗi dữ liệu sản phẩm: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnCart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("OPEN_CART", true) // gửi cờ để báo mở giỏ hàng
            startActivity(intent)
            finish() // tùy chọn: nếu muốn quay về trang chính
        }


        // Lấy productId từ Intent
        val productId = intent.getStringExtra("PRODUCT_ID")
        if (productId != null) {
            productDetailViewModel.fetchProductDetail(productId)
        } else {
            Toast.makeText(this, "Không tìm thấy sản phẩm!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
