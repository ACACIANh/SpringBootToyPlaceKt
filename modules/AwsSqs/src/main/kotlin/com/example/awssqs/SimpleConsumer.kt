package com.example.awssqs

import io.awspring.cloud.sqs.annotation.SqsListener
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class SimpleConsumer {
    @SqsListener(
        value = ["\${cloud.aws.sqs.queue-name}"],
//        acknowledgementMode = SqsListenerAcknowledgementMode.ALWAYS,
        messageVisibilitySeconds = "1"
    )
    fun consume(@Headers headers: Map<String, String>, @Payload data: Any) {
        log.info("-- Headers : $headers")
        log.info("-- Data : $data")
    }
}