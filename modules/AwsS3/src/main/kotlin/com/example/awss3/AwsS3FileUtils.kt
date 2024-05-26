package com.example.awss3

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AwsS3FileUtils(

    @Value("\${cloud.aws.cdn.url}")
    val cdnURL: String,
    @Value("\${spring.profiles.active}")
    val activeProfile: String,
) {
    fun prefix(domain: String) = "${activeProfile}/$domain"     // 양식은 커스텀하게 변경

    // 필요한 요구사항인지 확인하기
    fun urlWithCdn(uploadMultipart: UploadMultipart) = "$cdnURL/${uploadMultipart.processedFullFileName}"
}