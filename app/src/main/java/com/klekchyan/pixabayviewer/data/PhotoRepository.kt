package com.klekchyan.pixabayviewer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.klekchyan.pixabayviewer.network.PixabayApi
import com.klekchyan.pixabayviewer.network.PixabayApiService

class PhotoRepository {

    fun getPhotoContainers(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = PixabayApiService.MAX_PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = PixabayApiService.MAX_PAGE_SIZE * 3),
            pagingSourceFactory = { PixabayPagingSource(PixabayApi.pixabayApiService, query) }
        ).liveData

    fun getCategories(): List<Category>{
        return Category.values().toList()
    }
}
enum class Category{
    BACKGROUNDS,
    FASHION,
    NATURE,
    SCIENCE,
    EDUCATION,
    FEELINGS,
    HEALTH,
    PEOPLE,
    RELIGION,
    PLACES,
    ANIMALS,
    INDUSTRY,
    COMPUTER,
    FOOD,
    SPORTS,
    TRANSPORTATION,
    TRAVEL,
    BUILDINGS,
    BUSINESS,
    MUSIC
}