package com.klekchyan.pixabayviewer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.klekchyan.pixabayviewer.network.PixabayApiService

class PhotoRepository(private val pixabayApiService: PixabayApiService){
    fun getPhotoContainers(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = PixabayApiService.MAX_PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = PixabayApiService.MAX_PAGE_SIZE * 3),
            pagingSourceFactory = { PixabayPagingSource(pixabayApiService, query) }
        ).liveData
}