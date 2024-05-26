package com.example.awss3

import io.awspring.cloud.s3.ObjectMetadata
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UploadMultipart private constructor(
    val multipartFile: MultipartFile,
    email: String,
    prefix: String,
) : MultipartFile by multipartFile {

    val metadata = ObjectMetadata.builder()
        .contentLength(multipartFile.size)
        .contentType(multipartFile.contentType)
        .build()
    val processedFileName = "${getEmailId(email)}_${getCurrentFormattedTime()}_${multipartFile.originalFilename}"
    val processedFullFileName = "$prefix/$processedFileName"

    fun metadataToMap(): Map<String, String> {
        return mapOf(
            "contentLength" to metadata.contentLength.toString(),
            "contentType" to metadata.contentType.orEmpty()
        )
    }

    companion object {
        private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
        private fun validateEmail(email: String): String {
            if (!emailRegex.matches(email)) {
                throw IllegalArgumentException("Invalid email format: $email")
            }
            return email
        }

        private fun getEmailId(email: String): String {
            return email.substringBefore('@')
        }

        private fun getCurrentFormattedTime(): String {
            // 개선포인트 - dynamic with yml
            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            return LocalDateTime.now().format(formatter)
        }

        fun create(multipartFile: MultipartFile, email: String, prefix: String): UploadMultipart {
            validateEmail(email)
            return UploadMultipart(multipartFile, email, prefix)
        }
    }
}