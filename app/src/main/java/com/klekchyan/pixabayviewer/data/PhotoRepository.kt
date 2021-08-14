package com.klekchyan.pixabayviewer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.klekchyan.pixabayviewer.R
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
enum class Category(val colorId: Int){
    BACKGROUNDS(R.color.category_background),
    FASHION(R.color.category_fashion),
    NATURE(R.color.category_nature),
    SCIENCE(R.color.category_science),
    EDUCATION(R.color.category_education),
    FEELINGS(R.color.category_feelings),
    HEALTH(R.color.category_health),
    PEOPLE(R.color.category_people),
    RELIGION(R.color.category_religion),
    PLACES(R.color.category_places),
    ANIMALS(R.color.category_animals),
    INDUSTRY(R.color.category_industry),
    COMPUTER(R.color.category_computer),
    FOOD(R.color.category_food),
    SPORTS(R.color.category_sports),
    TRANSPORTATION(R.color.category_transportation),
    TRAVEL(R.color.category_travel),
    BUILDINGS(R.color.category_buildings),
    BUSINESS(R.color.category_business),
    MUSIC(R.color.category_music)
}