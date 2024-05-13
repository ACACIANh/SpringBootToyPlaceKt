package com.example.fcm.core

class FcmMessageData(
    val default: Default,
    val custom: Custom,
    val config: Config,
) {
    fun toAllData(): Map<String, String> {
        //개선 포인트 - json 으로 변환후 다시 map 으로?
        return mapOf(
            "contentsType" to custom.contentsType,
            "contentsId" to custom.contentsId,
        )
    }

    fun withDefaultAllData(): Map<String, String> {
        return mapOf(
            "title" to default.title,
            "body" to default.body,
            "image" to default.image,
            "contentsType" to custom.contentsType,
            "contentsId" to custom.contentsId,
        )
    }

    data class Default(
        val title: String,
        val body: String,
        val image: String,
    )

    data class Custom(
        val contentsType: String,
        val contentsId: String,
    )

    data class Config(
        val pushToken: String,
        val badgeCount: Int,
    )
}

