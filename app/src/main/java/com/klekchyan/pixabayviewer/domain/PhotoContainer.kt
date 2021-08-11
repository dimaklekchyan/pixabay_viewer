package com.klekchyan.pixabayviewer.domain

data class PhotoContainer(
    val id: Int,
    val type: String,
    val previewUrl: String,
    val webformatUrl: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    val largeImageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
)