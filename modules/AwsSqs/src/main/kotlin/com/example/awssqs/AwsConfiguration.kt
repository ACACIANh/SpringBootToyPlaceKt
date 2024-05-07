package com.example.awssqs

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class AwsConfiguration {

    @Bean
    fun sqsAsyncClient(
        @Value("\${cloud.aws.region.static}") region: String,
        @Value("\${cloud.aws.credentials.accessKey}") accessKey: String,
        @Value("\${cloud.aws.credentials.secretKey}") secretKey: String,
    ): SqsAsyncClient {

        return SqsAsyncClient.builder()
//            .endpointOverride(URI.create("static-url"))
            .region(Region.of(region))
            .credentialsProvider { AwsBasicCredentials.create(accessKey, secretKey) }
            .build()
    }

    @Bean
    fun defaultSqsListenerContainerFactory(sqsAsyncClient: SqsAsyncClient): SqsMessageListenerContainerFactory<Any> {
        return SqsMessageListenerContainerFactory
            .builder<Any>()
            .sqsAsyncClient(sqsAsyncClient)
            .build()
    }
}