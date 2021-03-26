package com.kmvdata.kotlin.demoflickrmvvm.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFlickrUtils {

    companion object {
        private val apiFlickr: ApiFlickr

        init {
            apiFlickr = Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiFlickr::class.java)
        }

        fun fetchPhotos(callback: Callback<FlickrResponse>) {
            val flickrRequest: Call<FlickrResponse> = apiFlickr.fetchPhotos()
            flickrRequest.enqueue(callback)
        }

        @WorkerThread
        fun fetchPhoto(url: String): Bitmap? {
            val response: Response<ResponseBody> = apiFlickr.fetchUrlBytes(url).execute()
            val bitmap = response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
            return bitmap
        }
    }
}
