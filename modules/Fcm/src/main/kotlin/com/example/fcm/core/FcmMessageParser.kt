package com.example.fcm.core

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Component

@Component
class FcmMessageParser(
    private val fcmConfigUtils: FcmConfigUtils,
) {
    fun parse(messageData: FcmMessageData): Message {
        val defaultNotification = messageData.default.let {
            Notification.builder()
                .setTitle(it.title)
                .setBody(it.body)
                .setImage(it.image)
                .build()
        }
        val androidConfig = fcmConfigUtils.toAndroidConfig(messageData)
        val apnsConfig = fcmConfigUtils.toApnsConfig(messageData)

        return Message.builder()
            .setNotification(defaultNotification)
            .setToken(messageData.config.pushToken)
            .putAllData(messageData.customDataMap())
            .setAndroidConfig(androidConfig)
            .setApnsConfig(apnsConfig)
            .build()
    }
}