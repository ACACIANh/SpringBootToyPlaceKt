package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class GracefulShutdownApplication

fun main(args: Array<String>) {
    runApplication<GracefulShutdownApplication>(*args)
}
