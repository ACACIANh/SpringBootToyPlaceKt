package com.example.awssqs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AwsSqsApplication

fun main(args: Array<String>) {
    runApplication<AwsSqsApplication>(*args)
}
