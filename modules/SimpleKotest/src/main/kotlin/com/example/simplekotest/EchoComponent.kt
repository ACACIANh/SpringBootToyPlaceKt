package com.example.simplekotest

import mu.KotlinLogging
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class EchoComponent {

    fun echo(value: String): String {
        log.info { value }
        return value
    }
}