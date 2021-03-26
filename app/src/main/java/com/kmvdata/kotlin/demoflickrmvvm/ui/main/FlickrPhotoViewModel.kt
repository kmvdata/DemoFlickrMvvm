package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kmvdata.kotlin.demoflickrmvvm.api.ApiFlickrUtils
import com.kmvdata.kotlin.demoflickrmvvm.api.FlickrResponse
import com.kmvdata.kotlin.demoflickrmvvm.api.GalleryItem
import com.kmvdata.kotlin.demoflickrmvvm.api.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TAG-FlickrPhotoVM"

class FlickrPhotoViewModel : ViewModel() {
    val galleryItems: MutableLiveData<List<GalleryItem>> = MutableLiveData()

    init {
        fetchPhotos()
    }

    fun fetchPhotos() {
        ApiFlickrUtils.fetchPhotos(object : Callback<FlickrResponse> {
            override fun onResponse(
                call: Call<FlickrResponse>,
                response: Response<FlickrResponse>
            ) {
                // The return value of response.body() is a deserialized response body of a successful response.
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                // it: implicit name of a single parameter
                // See: https://kotlinlang.org/docs/lambdas.html#it-implicit-name-of-a-single-parameter
                galleryItems.value = photoResponse?.garrlleryItems?.filterNot {
                    it.url.isBlank()
                }
                Log.d(TAG, "Response received: ${Gson().toJson(galleryItems)}")
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG, "onFailure:", t)
            }
        })
    }

    fun getGalleryItemCount(): Int {
        return galleryItems.value?.size ?: 0
    }

    fun getGalleryItem(position: Int): GalleryItem? {
        return galleryItems.value?.get(position)
    }
}
