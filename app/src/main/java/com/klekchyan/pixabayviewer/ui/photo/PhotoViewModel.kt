package com.klekchyan.pixabayviewer.ui.photo

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException


enum class WallpaperState{ SUCCESSFULLY_SET,  FAILURE}

class PhotoViewModel(application: Application): AndroidViewModel(application) {

    private var glide: RequestManager? = null
    private var windowManager: WindowManager? = null
    private var wallpaperManager: WallpaperManager? = null
    private var imageUrl: String? = null

    private val _settingWallpaperState = MutableLiveData<WallpaperState>()
    val settingWallpaperState: LiveData<WallpaperState>
        get() = _settingWallpaperState

    init {
        glide = Glide.with(application)
        windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wallpaperManager = WallpaperManager.getInstance(application)
    }

    fun setImageUrl(imageUrl: String?){
        this.imageUrl = imageUrl
    }

    fun onSetImageAsWallpaperButtonClicked(){
        viewModelScope.launch {
            try{
                glide?.let {
                    it.asBitmap()
                        .load(imageUrl?.toUri())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            setWallpaper(resource)
                            viewModelScope.launch(Dispatchers.Main) {
                                _settingWallpaperState.value = WallpaperState.SUCCESSFULLY_SET
                            }
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main){ _settingWallpaperState.value = WallpaperState.FAILURE }
                Timber.e("$ex")
            }
        }
    }

    private fun setWallpaper(bitmap: Bitmap){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
            wallpaperManager?.setBitmap(getFittedBitmapToSet(bitmap))
        } else {
            wallpaperManager?.setBitmap(getFittedBitmapToSet(bitmap), null, false, WallpaperManager.FLAG_SYSTEM )
        }
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

    private fun getPhoneMetrics(): Pair<Int, Int>{
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getRealMetrics(metrics)

        val phoneHeight = metrics.heightPixels
        val phoneWidth = metrics.widthPixels
        return phoneHeight to phoneWidth
    }

    override fun onCleared() {
        super.onCleared()
        glide = null
        windowManager = null
        wallpaperManager = null
    }
}