package com.example.retrofitdemoupdated.network

import com.example.retrofitdemoupdated.model.Post
import com.example.retrofitdemoupdated.model.PostItem
import retrofit2.Response
import retrofit2.http.GET



interface ApiService {
    @GET("posts")
    suspend fun getAllPost():Post
}