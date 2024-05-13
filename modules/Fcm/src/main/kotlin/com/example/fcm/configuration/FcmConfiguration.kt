package com.example.fcm.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FcmConfiguration(

    @Value("\${fcm.service-account-file}")
    val fcmFilePath: String,

    @Value("\${fcm.topic-name}")
    val topicName: String,

    @Value("\${fcm.project-id}")
    val projectId: String,
) {

    @PostConstruct
    fun initialize() {

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(ClassPathResource(fcmFilePath).inputStream))
            .setProjectId(projectId)
            .build()

        FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}