package com.example.handmade.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handmade.Model.Category
import com.example.handmade.Rest.RetrofitClient
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getCategories()
                _categories.value = result
            } catch (e: Exception) {
                Log.e("CategoryVM", "Error: ${e.message}")
            }
        }
    }
}
