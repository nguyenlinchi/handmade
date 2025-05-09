package com.example.handmade.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handmade.Adapter.SearchAdapter
import com.example.handmade.R
import com.example.handmade.ViewModel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var searchInput: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerSearch: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchInput = view.findViewById(R.id.searchInput)
        btnSearch = view.findViewById(R.id.btnSearch)
        recyclerSearch = view.findViewById(R.id.recyclerSearch)

        recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(emptyList())
        recyclerSearch.adapter = adapter

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        btnSearch.setOnClickListener {
            val keyword = searchInput.text.toString().trim()
            if (keyword.isNotEmpty()) {
                viewModel.searchProducts(keyword)
            }
        }

        viewModel.products.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        return view
    }
}
