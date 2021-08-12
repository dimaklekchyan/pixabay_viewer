package com.klekchyan.pixabayviewer.ui

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.klekchyan.pixabayviewer.R

@BindingAdapter("setImage")
fun ImageView.setImage(url: String?){
    url?.let {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_broken_image)
            .into(this)
    }
}