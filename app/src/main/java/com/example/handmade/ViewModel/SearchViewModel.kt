package com.example.handmade.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handmade.Model.Product
import com.example.handmade.Rest.RetrofitClient
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun searchProducts(keyword: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.searchProducts(keyword)
                _products.value = response
            } catch (e: Exception) {
                _products.value = emptyList()
            }
        }
    }
}
