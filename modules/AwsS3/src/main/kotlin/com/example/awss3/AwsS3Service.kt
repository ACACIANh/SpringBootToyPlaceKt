package com.example.awss3

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File

private val log = KotlinLogging.logger { }

@Service
class AwsS3Service(
    val s3Client: S3Client,

    @Value("\${cloud.aws.s3.bucket}")
    val bucket: String,
) {

    fun downloadLocal(destination: String, s3key: String) {
        val request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(s3key)
            .build()

        File(destination).parentFile.mkdirs()

        s3Client.getObject(request).use { input ->
            File(destination).outputStream().use { output ->
                input.copyTo(output)
            }
        }
        log.info("download complete : $destination")
    }

    fun uploadLocal(source: UploadMultipart): Result<String> {
        return this.uploadLocal(listOf(source))
    }

    fun uploadLocal(sources: List<UploadMultipart>): Result<String> {
        return runCatching {
            sources.forEach {
                val request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(it.processedFullFileName)
                    .metadata(it.metadataToMap())
                    .build()
                s3Client.putObject(request, RequestBody.fromBytes(it.bytes))
            }
            "S3 upload success"
        }.onFailure {
            // Best - CustomException
            throw RuntimeException("S3 upload fail")
        }
    }

    fun delete(s3key: String) {
        val request = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(s3key)
            .build()

        s3Client.deleteObject(request)
    }
}