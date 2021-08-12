package com.klekchyan.pixabayviewer.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klekchyan.pixabayviewer.data.Category
import com.klekchyan.pixabayviewer.data.PhotoRepository
import com.klekchyan.pixabayviewer.network.PixabayApi

class CategoriesViewModel: ViewModel() {
    private val repository = PhotoRepository()

    private val _categories = MutableLiveData<List<Category>>()
    private val _navigateToPhotoListFragment = MutableLiveData<Category?>()

    val categories: LiveData<List<Category>>
        get() = _categories
    val navigateToPhotoListFragment: LiveData<Category?>
        get() = _navigateToPhotoListFragment

    init {
        _categories.value = repository.getCategories()
    }

    fun onCategoryClicked(category: Category){
        _navigateToPhotoListFragment.value = category
    }

    fun navigateToPhotoListFragmentDone(){
        _navigateToPhotoListFragment.value = null
    }
}