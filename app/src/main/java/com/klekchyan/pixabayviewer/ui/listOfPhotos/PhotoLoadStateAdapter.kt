package com.klekchyan.pixabayviewer.ui.listOfPhotos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klekchyan.pixabayviewer.databinding.PhotoLoadStateBinding

class PhotoLoadStateAdapter(private val clickListener: PhotoLoadStateClickListener)
    : LoadStateAdapter<PhotoLoadStateAdapter.PhotoLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PhotoLoadStateViewHolder {
        return PhotoLoadStateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, clickListener)
    }

    class PhotoLoadStateViewHolder(private val binding: PhotoLoadStateBinding) : RecyclerView.ViewHolder(binding.root){

        companion object{
            fun from(parent: ViewGroup): PhotoLoadStateViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoLoadStateBinding.inflate(layoutInflater, parent, false)
                return PhotoLoadStateViewHolder(binding)
            }
        }

        fun bind(loadState: LoadState, listener: PhotoLoadStateClickListener){
            binding.apply {
                progressIndicator.isVisible = loadState == LoadState.Loading
                progressErrorText.isVisible = loadState != LoadState.Loading
                retryButton.isVisible = loadState != LoadState.Loading
                this.listener = listener
            }
        }
    }
}

class PhotoLoadStateClickListener(private val listener: () -> Unit){
    fun onClick() = listener()
}