package com.klekchyan.pixabayviewer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.klekchyan.pixabayviewer.domain.PhotoContainer
import com.klekchyan.pixabayviewer.network.PixabayApi
import com.klekchyan.pixabayviewer.network.PixabayApiService

class PhotoRepository(private val query: String){
    val data: LiveData<PagingData<PhotoContainer>> = Pager(
            config = PagingConfig(
                pageSize = PixabayApiService.MAX_PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = PixabayApiService.MAX_PAGE_SIZE * 5),
            pagingSourceFactory = { PixabayPagingSource(PixabayApi.pixabayApiService, query) }
        ).liveData
}