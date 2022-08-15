package com.example.myapplication

import com.example.myapplication.api.Data
import com.example.myapplication.api.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getSearchResult( @Query("api_key") key: String, @Query("q") searchValue: String, @Query("limit") limit: Int,
                         @Query("offset") offset: Int, @Query("rating") rating: String, @Query("lang") language: String,): ResponseWrapper
}



class Repository {

    var BASE_URL = "https://api.giphy.com/v1/gifs/"
    var API_KEY = "Nkw9qygtvSs8IyjodzBa3QQlvzK9ZSeB"
    var limit = 25
    var rating = "g"
    var language = "en"

    suspend fun getData(searchValue: String, offset: Int = 0): Flow<List<Data>> {

        return flow {
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            chain.proceed(chain.request())
                        }
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
            emit(
                retrofitBuilder.getSearchResult(API_KEY, searchValue, limit, offset, rating, language).data
            )
        }.flowOn(Dispatchers.IO)

    }
}