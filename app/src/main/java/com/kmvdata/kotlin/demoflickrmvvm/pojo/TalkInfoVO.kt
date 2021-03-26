package com.kmvdata.kotlin.demoflickrmvvm.pojo

import java.util.*

data class TalkInfoVO(
    val iconUrl: String,
    val talkName: String,
    val lastTime: Date,
    val isNotificationAllowed: Boolean
)
