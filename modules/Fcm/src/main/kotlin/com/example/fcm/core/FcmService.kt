package com.example.fcm.core

import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmMessageParser: FcmMessageParser,
    private val firebaseMessaging: FirebaseMessaging,
) {
    fun send(fcmMessage: FcmMessageData) {
        val message = fcmMessageParser.parse(fcmMessage)
        firebaseMessaging.send(message)
    }

    fun send(fcmMessages: List<FcmMessageData>): List<BatchResponse> {
        val messages = fcmMessages.map {
            fcmMessageParser.parse(it)
        }
        return this.send(messages)
    }

    private fun send(messages: List<Message>): List<BatchResponse> {
        if (messages.isEmpty()) {
            return listOf()
        }
        if (messages.size < FIREBASE_SEND_MAXIMUM) {
            return listOf(firebaseMessaging.sendEach(messages))
        }
        return messages.chunked(FIREBASE_SEND_MAXIMUM).map {
            firebaseMessaging.sendEach(it)
        }
    }

    companion object {
        const val FIREBASE_SEND_MAXIMUM = 500
    }
}