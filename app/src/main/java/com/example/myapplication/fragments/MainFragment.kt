package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.GifAdapter
import com.example.myapplication.viewModels.MyViewModel
import com.example.myapplication.R
import com.example.myapplication.extensions.textChanges
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private val adapter by lazy { GifAdapter() }
    private val viewModel: MyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(this.context)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView)
        val search = getView()?.findViewById<EditText>(R.id.searchText)

        adapter.listener =  { viewModel.getNextPage(search?.text.toString()) }

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.data.collect {
                    it?.let {
                        adapter.setItems(it)
                    }
                }
            }
        }

        search?.let { setupListeners(it, viewModel) }

    }

    private fun setupListeners(search: EditText, viewModel: MyViewModel){
        search?.textChanges()
            .filter { !it.isNullOrBlank() && it.length > 2 && search.isFocused }
            .debounce(250)
            .onEach { it?.let { viewModel.collectFlow(it.toString()) } }
            .launchIn(lifecycleScope)
    }


}