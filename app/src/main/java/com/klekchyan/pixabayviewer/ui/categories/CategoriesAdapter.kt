package com.klekchyan.pixabayviewer.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klekchyan.pixabayviewer.data.Category
import com.klekchyan.pixabayviewer.databinding.CategoryItemBinding

class CategoriesAdapter(private val clickListener: CategoryClickListener): ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoriesDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, clickListener)
    }

    class CategoryViewHolder(private val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun from(parent: ViewGroup): CategoryViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }

        fun bind(category: Category, listener: CategoryClickListener){
            binding.category = category
            binding.listener = listener
        }
    }

}

class CategoriesDiffCallBack: DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.name.lowercase() == newItem.name.lowercase()
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.name.lowercase() == newItem.name.lowercase()
    }
}

class CategoryClickListener(private val listener: (category: Category) -> Unit){
    fun onClick(category: Category) = listener(category)
}