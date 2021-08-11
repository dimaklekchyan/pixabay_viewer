package com.klekchyan.pixabayviewer.ui.listOfPhotos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.klekchyan.pixabayviewer.R
import com.klekchyan.pixabayviewer.databinding.FragmentPhotosListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotosListBinding? = null
    private val viewModel: PhotoListViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPhotosListBinding.inflate(inflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(viewLifecycleOwner, { response ->
            Timber.d(response.toString())
        })
    }
}