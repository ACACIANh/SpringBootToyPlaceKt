package com.example.awss3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AwsS3Application

fun main(args: Array<String>) {
    runApplication<AwsS3Application>(*args)
}
