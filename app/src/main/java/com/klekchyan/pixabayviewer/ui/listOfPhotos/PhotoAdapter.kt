package com.klekchyan.pixabayviewer.ui.listOfPhotos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.klekchyan.pixabayviewer.databinding.PhotoItemBinding
import com.klekchyan.pixabayviewer.domain.PhotoContainer

class PhotoAdapter(private val clickListener: PhotoAdapterClickListener): PagingDataAdapter<PhotoContainer, PhotoAdapter.PhotoViewHolder>(PhotoAdapterDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoContainer = getItem(position)
        holder.bind(photoContainer, clickListener)
    }

    class PhotoViewHolder(private val binding: PhotoItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup): PhotoViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoItemBinding.inflate(layoutInflater, parent, false)
                return PhotoViewHolder(binding)
            }
        }

        fun bind(photoContainer: PhotoContainer?, listener: PhotoAdapterClickListener){
            binding.photoContainer = photoContainer
            binding.listener = listener
        }
    }
}

class PhotoAdapterDiffCallBack: DiffUtil.ItemCallback<PhotoContainer>(){
    override fun areItemsTheSame(oldItem: PhotoContainer, newItem: PhotoContainer): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PhotoContainer, newItem: PhotoContainer): Boolean {
        return oldItem.id == newItem.id
    }
}

class PhotoAdapterClickListener(val clickListener: (photoContainer: PhotoContainer?) -> Unit){
    fun onClick(photoContainer: PhotoContainer?) = clickListener(photoContainer)
}