package com.klekchyan.pixabayviewer.ui.listOfPhotos

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.klekchyan.pixabayviewer.data.PhotoRepository
import com.klekchyan.pixabayviewer.domain.PhotoContainer

class PhotoListViewModel(query: String): ViewModel() {
    private val photoRepository = PhotoRepository()

    private val _navigateToPhotoFragment = MutableLiveData<PhotoContainer?>()
    val navigateToPhotoFragment: LiveData<PhotoContainer?>
        get() = _navigateToPhotoFragment
    val photoContainers = photoRepository.getPhotoContainers(query).cachedIn(viewModelScope)

    fun onPhotoClicked(photoContainer: PhotoContainer?){
        _navigateToPhotoFragment.value = photoContainer
    }

    fun navigateToPhotoFragmentDone(){
        _navigateToPhotoFragment.value = null
    }

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