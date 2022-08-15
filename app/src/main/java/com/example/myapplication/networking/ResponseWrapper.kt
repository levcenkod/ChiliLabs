package com.example.myapplication.api

data class Data(
    val images: Images
)

data class Images(
    val original: Original
)

data class Original(
    val url: String
)

data class Pagination(
    val count: Int,
    val offset: Int,
    val total_count: Int
)
data class ResponseWrapper (
    val data: List<Data>,
    val pagination: Pagination
    )
