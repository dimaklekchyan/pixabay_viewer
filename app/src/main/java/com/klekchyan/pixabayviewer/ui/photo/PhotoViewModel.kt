package com.klekchyan.pixabayviewer.ui.photo

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException


enum class WallpaperState{ SUCCESSFULLY_SET,  FAILURE}

class PhotoViewModel(private val imageUrl: String, application: Application): AndroidViewModel(application) {

    private val glide = Glide.with(application)
    private val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val wallpaperManager = WallpaperManager.getInstance(application)

    private val _settingWallpaperState = MutableLiveData<WallpaperState>()
    val settingWallpaperState: LiveData<WallpaperState>
        get() = _settingWallpaperState

    val photoUrl: LiveData<String> = liveData { emit(imageUrl) }

    var bitmap: Bitmap? = null

    fun setImageAsWallpaper(){

        if(bitmap == null){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    glide.asBitmap()
                        .load(imageUrl.toUri())
                        .into(object : CustomTarget<Bitmap>() {
                            @RequiresApi(Build.VERSION_CODES.N)
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                bitmap = resource//
                            }
                            override fun onLoadCleared(placeholder: Drawable?) {
                            }
                        })
                } catch (ex: IOException) {
                    _settingWallpaperState.value = WallpaperState.FAILURE
                    Timber.e("$ex")
                }
            }
        } else setWallpaper(bitmap!!)
    }

    private fun getPhoneMetrics(): Pair<Int, Int>{
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)

        val phoneHeight = metrics.heightPixels
        val phoneWidth = metrics.widthPixels
        return phoneHeight to phoneWidth
    }

    private fun getFittedBitmapToSet(originalBitmap: Bitmap): Bitmap{
        val (phoneHeight, phoneWidth) = getPhoneMetrics()

        val imageHeight = originalBitmap.height
        val imageWidth = originalBitmap.width

        val scale: Float = phoneHeight.toFloat() / imageHeight.toFloat()

        val scaledBitmap = Bitmap.createScaledBitmap(
            originalBitmap,
            (imageWidth * scale).toInt(), (imageHeight * scale).toInt(),
            false)

        val x = (scaledBitmap.width / 2) - (phoneWidth / 2)

        return Bitmap.createBitmap(scaledBitmap, x, 0, phoneWidth, phoneHeight)
    }

    private fun setWallpaper(bitmap: Bitmap){
        try {
            val fittedBitmap = getFittedBitmapToSet(bitmap)
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                wallpaperManager.setBitmap(fittedBitmap)
            } else {
                wallpaperManager.setBitmap(
                    fittedBitmap,
                    null,
                    false,
                    WallpaperManager.FLAG_SYSTEM )
                _settingWallpaperState.value = WallpaperState.SUCCESSFULLY_SET
            }
        } catch (ex: Exception){
            _settingWallpaperState.value = WallpaperState.FAILURE
            Timber.e("$ex")
        }
    }
}

class PhotoViewModelFactory(
    private val imageUrl: String,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(imageUrl, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}