package com.klekchyan.pixabayviewer.network

import com.klekchyan.pixabayviewer.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://pixabay.com/"
private const val API = "api/"

private const val PARAMETER_KEY = "key"
private const val PARAMETER_CATEGORY = "category"
private const val PARAMETER_PAGE = "page"
private const val PARAMETER_PER_PAGE = "per_page"

private const val API_KEY = BuildConfig.API_KEY

private val client = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PixabayApiService {
    @GET(API)
    suspend fun getPhotosByCategory(
        @Query(PARAMETER_KEY) key: String = API_KEY,
        @Query(PARAMETER_CATEGORY) category: String,
        @Query(PARAMETER_PAGE) page: Int,
        @Query(PARAMETER_PER_PAGE) perPage: Int
    ) : PhotoContainerNetworkModel

    companion object {
        const val MAX_PAGE_SIZE = 10
    }
}

object PixabayApi {
    val pixabayApiService: PixabayApiService by lazy {
        retrofit.create(PixabayApiService::class.java)
    }
}