package com.klekchyan.pixabayviewer.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://pixabay.com/"
private const val API = "api/"

private const val PARAMETER_KEY = "key"
private const val PARAMETER_CATEGORY = "category"
private const val PARAMETER_PAGE = "page"
private const val PARAMETER_PER_PAGE = "per_page"

private val client = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

//private val retrofit = Retrofit.Builder()
//    .client(client)
//    .addCallAdapterFactory()
//    .baseUrl(BASE_URL)
//    .build()

interface PixabayApiService {
    @GET(API)
    fun getPhotosByCategoryAsync(
        @Query(PARAMETER_KEY) key: String = "KEY",
        @Query(PARAMETER_CATEGORY) category: String,
        @Query(PARAMETER_PAGE) page: Int,
        @Query(PARAMETER_PER_PAGE) perPage: Int
    )
}