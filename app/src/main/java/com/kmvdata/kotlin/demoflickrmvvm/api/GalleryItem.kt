package com.kmvdata.kotlin.demoflickrmvvm.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GalleryItem(
    var title: String = "",
    var id: String = "",
    @SerializedName("url_s")
    var url: String = ""
) : Serializable
