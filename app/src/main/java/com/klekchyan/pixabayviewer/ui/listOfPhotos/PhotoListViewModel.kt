package com.klekchyan.pixabayviewer.ui.listOfPhotos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.klekchyan.pixabayviewer.data.PhotoRepository
import com.klekchyan.pixabayviewer.network.PixabayApi

class PhotoListViewModel(query: String): ViewModel() {
    private val photoRepository = PhotoRepository(PixabayApi.pixabayApiService)
    val photoContainers = photoRepository.getPhotoContainers(query).cachedIn(viewModelScope)
}

class PhotoListViewModelFactory(
    private val query: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoListViewModel::class.java)) {
            return PhotoListViewModel(query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}