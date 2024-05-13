package com.example.fcm.core

import com.example.fcm.endpoint.PushRequest
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Message

interface FcmMessageParser {

    fun parse(customMessage: FcmMessageData, androidConfig: AndroidConfig, apnsConfig: ApnsConfig): Message

    // 다른 계층으로?
    fun parse(pushRequest: PushRequest): FcmMessageData
}