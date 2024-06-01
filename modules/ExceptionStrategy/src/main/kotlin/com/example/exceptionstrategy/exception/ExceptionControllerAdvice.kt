package com.example.exceptionstrategy.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val log = KotlinLogging.logger { }

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleInternalServer(e: Exception): ResponseEntity<ExceptionResponse> {
        log.error("Internal Server Error occurred: ${e.message}", e)
        val response = ExceptionResponse(ExceptionCode.INTERNAL_ERROR)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(e: ApplicationException): ResponseEntity<ExceptionResponse> {
        log.error("Application Server Error occurred: ${e.message}", e)
        val response = ExceptionResponse(e)
        return ResponseEntity.status(e.exceptionCode.httpStatus).body(response)
    }
    // status 가 중복인 이유 -> 200 으로 처리하고 내부 response 모델에서 status 를 관리하는 경우를 위함 예전 카카오 API

}