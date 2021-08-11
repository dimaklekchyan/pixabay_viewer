package com.klekchyan.pixabayviewer.ui.listOfPhotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klekchyan.pixabayviewer.network.PhotoContainerNetworkModel
import com.klekchyan.pixabayviewer.network.PixabayApi
import com.klekchyan.pixabayviewer.data.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoListViewModel: ViewModel() {
    private val photoRepository = PhotoRepository()
    private val _list = MutableLiveData<PhotoContainerNetworkModel>()
    val list: LiveData<PhotoContainerNetworkModel>
        get() = _list

    init{
        viewModelScope.launch {
            val response = PixabayApi.pixabayApiService.getPhotosByCategory(category = "health", page = 1, perPage = 30)
            withContext(Dispatchers.Main){
                _list.value = response
            }
        }
    }
}