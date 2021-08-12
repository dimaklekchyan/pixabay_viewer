package com.klekchyan.pixabayviewer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klekchyan.pixabayviewer.domain.PhotoContainer
import com.klekchyan.pixabayviewer.network.PixabayApi
import com.klekchyan.pixabayviewer.network.PixabayApiService
import com.klekchyan.pixabayviewer.network.toListOfPhotoContainer
import retrofit2.HttpException
import java.io.IOException

class PixabayPagingSource(
    private val service: PixabayApiService,
    private val query: String
): PagingSource<Int, PhotoContainer>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoContainer>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoContainer> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize = params.loadSize
        return try{
            val response = service.getPhotosByCategory(category = query, page = pageNumber, perPage = pageSize)
            val photoContainers = response.toListOfPhotoContainer()
            val nextKey = if(photoContainers.isEmpty()) null else pageNumber + (params.loadSize / PixabayApiService.MAX_PAGE_SIZE)
            val prevKey = if(pageNumber == INITIAL_PAGE_NUMBER) null else pageNumber - 1
            LoadResult.Page(data = photoContainers, prevKey = prevKey, nextKey = nextKey)
        } catch (ex: IOException){
            LoadResult.Error(ex)
        } catch (ex: HttpException){
            LoadResult.Error(ex)
        }
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}