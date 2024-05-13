package com.example.fcm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FcmApplication

fun main(args: Array<String>) {
    runApplication<FcmApplication>(*args)
}
