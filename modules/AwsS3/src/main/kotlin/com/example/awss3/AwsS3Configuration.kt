package com.example.awss3

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AwsS3Configuration {

    @Bean
    fun s3Client(
        @Value("\${cloud.aws.region.static}") region: String,
        @Value("\${cloud.aws.credentials.accessKey}") accessKey: String,
        @Value("\${cloud.aws.credentials.secretKey}") secretKey: String,
    ): S3Client {

        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider { AwsBasicCredentials.create(accessKey, secretKey) }
            .build()
    }
}