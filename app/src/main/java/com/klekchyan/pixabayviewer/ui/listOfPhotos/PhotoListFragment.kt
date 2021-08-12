package com.klekchyan.pixabayviewer.ui.listOfPhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.klekchyan.pixabayviewer.databinding.FragmentPhotosListBinding
import timber.log.Timber

class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotosListBinding? = null
    private val binding get() = _binding!!
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter(PhotoLoadStateClickListener{ adapter.retry() }),
                footer = PhotoLoadStateAdapter(PhotoLoadStateClickListener{ adapter.retry() })
            )
            recyclerView.setHasFixedSize(true)
        }

        viewModel.photoContainers.observe(viewLifecycleOwner, { data ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}