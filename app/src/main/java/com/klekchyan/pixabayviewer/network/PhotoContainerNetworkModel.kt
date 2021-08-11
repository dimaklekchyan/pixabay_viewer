package com.klekchyan.pixabayviewer.network

import com.google.gson.annotations.SerializedName
import com.klekchyan.pixabayviewer.domain.PhotoContainer

data class PhotoContainerNetworkModel(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
)

data class Hit(
    val id: Int,
    val type: String,
    @SerializedName("previewURL")
    val previewUrl: String,
    @SerializedName("webformatURL")
    val webformatUrl: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    @SerializedName("largeImageURL")
    val largeImageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
)

fun PhotoContainerNetworkModel.toListOfPhotoContainer(): List<PhotoContainer>{
    return this.hits.map { hit ->
        PhotoContainer(
            id = hit.id,
            type = hit.type,
            previewUrl = hit.previewUrl,
            webformatUrl = hit.webformatUrl,
            webformatWidth = hit.webformatWidth,
            webformatHeight = hit.webformatHeight,
            largeImageUrl = hit.largeImageUrl,
            imageWidth = hit.imageWidth,
            imageHeight = hit.imageHeight
        )
    }
}