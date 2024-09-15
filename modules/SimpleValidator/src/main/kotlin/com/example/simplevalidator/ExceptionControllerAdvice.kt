package com.example.simplevalidator

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

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
        log.error("HTTP Message Not Readable occurred: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: ${e.message}")
    }

    // request type mismatch 로 인한 exception
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleException(e: MethodArgumentTypeMismatchException): ResponseEntity<String> {
        log.error("Method Argument Type Mismatch occurred: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: ${e.message}")
    }

    // request parameter 누락으로 인한 exception
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleException(e: MissingServletRequestParameterException): ResponseEntity<String> {
        log.error("Missing Servlet Request Parameter occurred: ${e.message}", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: ${e.message}")
    }

    // spring validation 으로 인한 exception
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException): ResponseEntity<String> {
        val errors = e.bindingResult.fieldErrors
            .groupBy(FieldError::getDefaultMessage)
            .mapValues { it.value.map(FieldError::getField) }
        val message =
            if (errors.isEmpty()) {
                e.bindingResult.allErrors.first().defaultMessage.toString()
            } else {
                errors.toString()
            }
        log.error("Method Argument NotValid occurred: $message", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message)
    }
}
