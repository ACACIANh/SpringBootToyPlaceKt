package com.example.fcm.endpoint

import com.example.fcm.core.FcmMessageData
import org.springframework.stereotype.Component

@Component
class FcmSimpleMessageParser {

    fun parse(fcmSimpleRequest: FcmSimpleRequest): FcmMessageData {
        return FcmMessageData(
            FcmMessageData.Default(fcmSimpleRequest.title, fcmSimpleRequest.body, fcmSimpleRequest.image),
            FcmMessageData.Custom(fcmSimpleRequest.contentsType, fcmSimpleRequest.contentsId),
            FcmMessageData.Config(fcmSimpleRequest.pushToken, fcmSimpleRequest.badgeCount),
        )
    }
}