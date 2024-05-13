package com.example.fcm.core

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.ApnsFcmOptions
import com.google.firebase.messaging.Aps
import org.springframework.stereotype.Component

@Component
class FcmConfigUtils {

    fun toAndroidConfig(fcmMessageData: FcmMessageData): AndroidConfig {

        return AndroidConfig.builder()
            .setNotification(this.toAndroidNotification(fcmMessageData))
            .putAllData(fcmMessageData.toAllData())
            .putData("level", "toAndroidConfig")        // 테스트용 - 확인후 제거
            .build()
    }

    private fun toAndroidNotification(fcmMessageData: FcmMessageData): AndroidNotification {
        val default = fcmMessageData.default
        return AndroidNotification.builder()
            .setTitle(default.title + " :androidNotification")  // 테스트용 - 확인후 제거
            .setBody(default.body + " :androidNotification")    // 테스트용 - 확인후 제거
            .setImage(default.image + " :androidNotification")  // 테스트용 - 확인후 제거
            .setDefaultSound(true)
            .build()
    }

    fun toApnsConfig(fcmMessageData: FcmMessageData): ApnsConfig {

        return ApnsConfig.builder()
            .setFcmOptions(this.toApnsFcmOptions(fcmMessageData.default))
            .setAps(this.toApns(fcmMessageData.config))
            .build()
    }

    private fun toApnsFcmOptions(default: FcmMessageData.Default): ApnsFcmOptions {
        return ApnsFcmOptions.builder()
            .setImage(default.image)
            .build()
    }

    private fun toApns(config: FcmMessageData.Config): Aps {
        return Aps.builder()
            .setBadge(config.badgeCount)
            .setSound("default") // 적절하게 개선하기
            .build()
    }
}