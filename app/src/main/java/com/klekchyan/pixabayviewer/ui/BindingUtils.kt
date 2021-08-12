package com.klekchyan.pixabayviewer.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.klekchyan.pixabayviewer.R

//PhotoListFragment
@BindingAdapter("setImage")
fun ImageView.setImage(url: String?){
    url?.let {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .optionalCenterCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_broken_image)
            .into(this)
    }
}

//CategoriesFragment
@BindingAdapter("setText")
fun TextView.setText(title: String?){
    val text = title?.lowercase()?.replaceFirstChar { title.first().uppercase() }
    this.text = text
}