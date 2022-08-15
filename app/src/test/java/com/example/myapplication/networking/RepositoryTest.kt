package com.example.myapplication.networking

import com.example.myapplication.Repository
import com.example.myapplication.api.Data
import com.example.myapplication.api.ResponseWrapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Test

class RepositoryTest {

    @Test
    fun shouldGetNotEmptyListFromApi() {
        runTest {
            val _data = MutableStateFlow<List<Data>?>(null)
            val data = _data.asStateFlow()

            Repository().getData("cat").collect(){
                _data.value = it
            }
            assertNotEquals(data.value, emptyList<Data>())
        }
    }

    @Test
    fun shouldGetEmptyListFromApi(){
        runTest {
            val _data = MutableStateFlow<List<Data>?>(null)
            val data = _data.asStateFlow()

            Repository().getData("dsfsadfsdfssfsf").collect(){
                _data.value = it
            }
            assertEquals(data.value, emptyList<Data>())
        }
    }
}