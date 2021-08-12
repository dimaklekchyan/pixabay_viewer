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
        return PhotoLoadStateViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: PhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class PhotoLoadStateViewHolder(
        private val binding: PhotoLoadStateBinding,
        private val listener: PhotoLoadStateClickListener) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener {
                listener.onClick()
            }
        }

        companion object{
            fun from(parent: ViewGroup, listener: PhotoLoadStateClickListener): PhotoLoadStateViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoLoadStateBinding.inflate(layoutInflater, parent, false)
                return PhotoLoadStateViewHolder(binding, listener)
            }
        }

        fun bind(loadState: LoadState){
            binding.apply {
                progressIndicator.isVisible = loadState == LoadState.Loading
                progressErrorText.isVisible = loadState != LoadState.Loading
                retryButton.isVisible = loadState != LoadState.Loading
            }
        }
    }
}

class PhotoLoadStateClickListener(private val listener: () -> Unit){
    fun onClick() = listener()
}