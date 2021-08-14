package com.klekchyan.pixabayviewer.ui.photo

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.klekchyan.pixabayviewer.databinding.FragmentPhotoBinding
import com.klekchyan.pixabayviewer.ui.setImage

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotoViewModel by viewModels()
    private var toast: Toast? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPhotoBinding.inflate(inflater)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = PhotoFragmentArgs.fromBundle(requireArguments()).imageUrl

        binding.wallpaper.setImage(imageUrl)
        binding.viewModel = viewModel

        viewModel.setImageUrl(imageUrl)
        viewModel.settingWallpaperState.observe(viewLifecycleOwner, { state ->
            when(state){
                WallpaperState.SUCCESSFULLY_SET -> showToast("Wallpaper was set")
                else -> showToast("Failure. Wallpaper was not set")
            }
        })
    }

    private fun showToast(text: String){
        toast?.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}