package com.example.simplekotest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleKotestApplication

fun main(args: Array<String>) {
    runApplication<SimpleKotestApplication>(*args)
}
