package com.klekchyan.pixabayviewer.ui.listOfPhotos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klekchyan.pixabayviewer.R
import com.klekchyan.pixabayviewer.databinding.FragmentPhotosListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotosListBinding? = null
    private val viewModel: PhotoListViewModel by viewModels {
        PhotoListViewModelFactory("health")
    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PhotoAdapter(PhotoAdapterClickListener {
            //TODO add navigate to specific photo
            Timber.d("${it?.id}")
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPhotosListBinding.inflate(inflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = _binding!!

        val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, { data ->
            adapter.submitData(lifecycle, data)
        })
    }
}