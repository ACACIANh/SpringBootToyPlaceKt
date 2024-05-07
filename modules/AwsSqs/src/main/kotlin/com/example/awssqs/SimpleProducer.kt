package com.example.awssqs

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

private val log = KotlinLogging.logger { }

@Component
class SimpleProducer(
    private val sqsAsyncClient: SqsAsyncClient,
) {
    fun produce(request: SimpleRequest) {
        val queueUrlRequest = GetQueueUrlRequest.builder().queueName(request.destinationQueueName).build()

        val queueURL = runCatching {
            sqsAsyncClient.getQueueUrl(queueUrlRequest).get()
        }.onSuccess {
            log.info { "Found queue : ${it.queueUrl()}" }
        }.onFailure {
            throw CustomException(HttpStatus.NOT_FOUND, "Not found queue : ${request.destinationQueueName}")
        }.getOrThrow()
            .queueUrl()

        val messageRequest = SendMessageRequest.builder()
            .queueUrl(queueURL)
            .messageBody(request.data.toString())
            .build()

        sqsAsyncClient.sendMessage(messageRequest)
            .thenAccept { log.info { "SendMessageResponse : $it" } }
    }
}
