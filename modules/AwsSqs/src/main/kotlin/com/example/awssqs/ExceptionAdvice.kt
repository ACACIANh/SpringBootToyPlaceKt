package com.example.awssqs

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val log = KotlinLogging.logger { }

@ControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(Exception::class)
    fun handleInternal(e: Exception): ResponseEntity<String> {
        log.error("Internal Server Error occurred: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustom(e: CustomException): ResponseEntity<String> {
        log.error("Custom Exception occurred: ${e.message}", e)
        return ResponseEntity.status(e.status).body(e.message)
    }
}

data class CustomException(
    val status: HttpStatus,
    override val message: String,
) : Exception()