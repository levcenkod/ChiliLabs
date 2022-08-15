package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Repository
import com.example.myapplication.api.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MyViewModel : ViewModel() {

    private val repos: Repository = Repository()
    private var offset = 0
    companion object { private const val step = 25 }

    private var _data = MutableStateFlow<List<Data>?>(null)
    val data = _data.asStateFlow()

    fun collectFlow(searchValue: String){
        offset = 0
        viewModelScope.launch {
            repos.getData(searchValue).collect{
                _data.value = it
            }
        }
    }

    fun getNextPage(searchValue: String){
        offset = offset.inc()
        viewModelScope.launch {
            repos.getData(searchValue, offset*step).collect{
                val oldData: ArrayList<Data> = ArrayList(_data.value)
                oldData.addAll(it)
                _data.value = oldData
            }
        }
    }

}