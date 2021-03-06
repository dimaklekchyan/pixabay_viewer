package com.klekchyan.pixabayviewer.ui

import android.content.res.Resources
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.card.MaterialCardView
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
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.ic_broken_image)
            .into(this)
    }
}

//CategoriesFragment
@BindingAdapter("setText")
fun TextView.setFittedText(title: String?){
    val text = title?.lowercase()?.replaceFirstChar { title.first().uppercase() }
    this.text = text
}

@BindingAdapter("setColor")
fun MaterialCardView.setColor(colorId: Int?){
    val color2 = ContextCompat.getColor(context, colorId!!)
    setCardBackgroundColor(color2)
}