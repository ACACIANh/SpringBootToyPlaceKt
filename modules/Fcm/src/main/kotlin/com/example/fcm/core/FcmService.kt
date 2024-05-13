package com.example.fcm.core

import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmMessageParser: FcmMessageParser,
    private val fcmConfigUtils: FcmConfigUtils,
    private val firebaseMessaging: FirebaseMessaging,
) {

    fun push(fcmMessage: FcmMessageData) {
        val apnConfig = fcmConfigUtils.toApnsConfig(fcmMessage)
        val androidConfig = fcmConfigUtils.toAndroidConfig(fcmMessage)
        val message = fcmMessageParser.parse(fcmMessage, androidConfig, apnConfig) // 구조개선
        firebaseMessaging.send(message) // 여러건에 대해서 sendEach
    }
}