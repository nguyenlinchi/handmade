package com.example.handmade.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.handmade.Adapter.CategoryAdapter
import com.example.handmade.Adapter.BannerAdapter
import com.example.handmade.Adapter.ProductAdapter
import com.example.handmade.R
import com.example.handmade.ViewModel.CategoryViewModel
import com.example.handmade.ViewModel.ProductViewModel

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex = 0
    private lateinit var imageUrls: List<String>

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewProduct: RecyclerView
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productViewModel: ProductViewModel

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // ViewPager setup for banners
        viewPager = view.findViewById(R.id.viewPagerBanner)
        imageUrls = listOf(
            "https://res.cloudinary.com/dumvx2lsj/image/upload/v1745725577/slider_1_i1nwce.jpg",
            "https://res.cloudinary.com/dumvx2lsj/image/upload/v1745725577/banner2_mxgwqc.jpg",
            "https://res.cloudinary.com/dumvx2lsj/image/upload/v1745725577/hahdbuv74k94i1zqkixx.jpg"
        )
        viewPager.adapter = BannerAdapter(imageUrls)

        // Auto slide banners
        autoSlideBanner()

        // Setup RecyclerView for categories
        recyclerViewCategory = view.findViewById(R.id.viewCategory)
        recyclerViewCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Category ViewModel
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            // Set up CategoryAdapter with the list of categories
            recyclerViewCategory.adapter = CategoryAdapter(categories)
        }
        categoryViewModel.fetchCategories()

        // Setup RecyclerView for products
        recyclerViewProduct = view.findViewById(R.id.viewBestSeller)
        recyclerViewProduct.layoutManager = LinearLayoutManager(context)

        // Product ViewModel
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.products.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productAdapter = ProductAdapter(products,requireContext()) // Pass the product list directly
                recyclerViewProduct.adapter = productAdapter
            } else {
                Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_SHORT).show()
            }
        }
        productViewModel.fetchProducts()

        return view
    }

    private fun autoSlideBanner() {
        val runnable = object : Runnable {
            override fun run() {
                currentIndex = (currentIndex + 1) % imageUrls.size
                viewPager.setCurrentItem(currentIndex, true)
                handler.postDelayed(this, 3000) // 3 seconds per image
            }
        }
        handler.post(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
