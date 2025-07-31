package com.example.annotationtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnnotationTestApplication

fun main(args: Array<String>) {
    runApplication<AnnotationTestApplication>(*args)
}