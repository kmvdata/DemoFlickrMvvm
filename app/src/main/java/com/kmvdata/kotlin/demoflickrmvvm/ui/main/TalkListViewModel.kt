package com.kmvdata.kotlin.demoflickrmvvm.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kmvdata.kotlin.demoflickrmvvm.api.ApiFlickrUtils
import com.kmvdata.kotlin.demoflickrmvvm.api.FlickrResponse
import com.kmvdata.kotlin.demoflickrmvvm.api.GalleryItem
import com.kmvdata.kotlin.demoflickrmvvm.api.PhotoResponse
import com.kmvdata.kotlin.demoflickrmvvm.pojo.TalkInfoVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "TAG-TalkViewModel"

class TalkListViewModel : ViewModel() {
    val talkInfoes: MutableLiveData<List<TalkInfoVO>> = MutableLiveData()

    init {
        mockTalkInfoList()
    }

    fun mockTalkInfoList() {
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

                val garrlleryItems = photoResponse?.garrlleryItems?.filterNot {
                    it.url.isBlank()
                } ?: return

                val talkInfoVOList = ArrayList<TalkInfoVO>(garrlleryItems.size)
                garrlleryItems.forEach {
                    talkInfoVOList.add(TalkInfoVO(it.url, it.title, Date(), true))
                }
                talkInfoes.value = talkInfoVOList
                Log.d(TAG, "Response received: ${Gson().toJson(talkInfoVOList)}")
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG, "onFailure:", t)
            }
        })
    }

    fun getTalkInfoCount(): Int {
        return talkInfoes.value?.size ?: 0
    }

    fun getTalkInfoItem(position: Int): TalkInfoVO? {
        return talkInfoes.value?.get(position)
    }
}