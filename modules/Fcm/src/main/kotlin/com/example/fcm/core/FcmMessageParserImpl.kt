package com.example.fcm.core

import com.example.fcm.endpoint.PushRequest
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Component

@Component
class FcmMessageParserImpl : FcmMessageParser {

    override fun parse(messageData: FcmMessageData, androidConfig: AndroidConfig, apnsConfig: ApnsConfig): Message {

        val defaultNotification = messageData.default.let {
            Notification.builder()
                .setTitle(it.title)
                .setBody(it.body)
                .setImage(it.image)
                .build()
        }

        return Message.builder()
            .setNotification(defaultNotification)
            .setToken(messageData.config.pushToken)
            .putAllData(messageData.toAllData())
            .setAndroidConfig(androidConfig)
            .setApnsConfig(apnsConfig)
            .build()
    }

    override fun parse(pushRequest: PushRequest): FcmMessageData {
        return FcmMessageData(
            FcmMessageData.Default(pushRequest.title, pushRequest.body, pushRequest.image),
            FcmMessageData.Custom(pushRequest.contentsType, pushRequest.contentsId),
            FcmMessageData.Config(pushRequest.pushToken, pushRequest.badgeCount),
        )
    }
}