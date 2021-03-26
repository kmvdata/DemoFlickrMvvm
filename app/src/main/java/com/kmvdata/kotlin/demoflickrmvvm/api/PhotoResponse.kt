package com.kmvdata.kotlin.demoflickrmvvm.api

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var garrlleryItems: List<GalleryItem>
}
