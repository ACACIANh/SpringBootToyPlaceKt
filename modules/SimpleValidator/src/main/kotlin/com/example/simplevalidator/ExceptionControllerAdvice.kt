package com.example.simplevalidator

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val log = KotlinLogging.logger { }

@ControllerAdvice
class ExceptionControllerAdvice {

    // common exception
    @ExceptionHandler(Exception::class)
    fun handleInternalServer(e: Exception): ResponseEntity<String> {
        log.error("Internal Server Error occurred: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }

    // not null type 에 null 입력으로 인한 exception
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleException(e: HttpMessageNotReadableException): ResponseEntity<String> {
        // 개선포인트 : message 를 예쁘게 정리해서 리턴하기
        log.error("HTTP Message Not Readable Error occurred: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: ${e.message}")
    }

    // spring validation 으로 인한 exception
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException): ResponseEntity<String> {
        val errors = e.bindingResult.fieldErrors
            .groupBy(FieldError::getDefaultMessage)
            .mapValues { it.value.map(FieldError::getField) }
        val message = errors.toString()
        log.error("Validation Error occurred: $message", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message)
    }
}