package com.klekchyan.pixabayviewer.ui.listOfPhotos

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.klekchyan.pixabayviewer.network.PhotoContainerNetworkModel
import com.klekchyan.pixabayviewer.network.PixabayApi
import com.klekchyan.pixabayviewer.data.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoListViewModel(private val query: String): ViewModel() {
    private val photoRepository = PhotoRepository(query)
    val data = photoRepository.data
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