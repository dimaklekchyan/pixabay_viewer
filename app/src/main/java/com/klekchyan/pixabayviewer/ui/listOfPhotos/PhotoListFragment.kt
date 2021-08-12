package com.klekchyan.pixabayviewer.ui.listOfPhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.klekchyan.pixabayviewer.databinding.FragmentPhotosListBinding

class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotosListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoListViewModel by viewModels {
        PhotoListViewModelFactory(PhotoListFragmentArgs.fromBundle(requireArguments()).category)
    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PhotoAdapter(PhotoAdapterClickListener { photoContainer ->
            viewModel.onPhotoClicked(photoContainer)
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

        viewModel.navigateToPhotoFragment.observe(viewLifecycleOwner, { photoContainer ->
            photoContainer?.let {
                findNavController()
                    .navigate(PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(it.largeImageUrl))
                viewModel.navigateToPhotoFragmentDone()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}