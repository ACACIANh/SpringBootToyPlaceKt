package com.example.fcm.endpoint

data class FcmSimpleRequest(
    val title: String,
    val body: String,
    val image: String,
    val contentsType: String,
    val contentsId: String,
    val pushToken: String,
    val badgeCount: Int,
)