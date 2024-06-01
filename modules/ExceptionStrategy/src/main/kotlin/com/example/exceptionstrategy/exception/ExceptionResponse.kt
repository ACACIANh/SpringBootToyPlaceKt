package com.example.exceptionstrategy.exception

import org.springframework.http.HttpStatus

data class ExceptionResponse(
    val httpStatus: HttpStatus,
    val serialCode: String,
    val message: String,
) {
    constructor(exception: ApplicationException) : this(
        exception.exceptionCode.httpStatus,
        exception.exceptionCode.serialCode(),
        exception.message
    )

    constructor(exceptionCode: IException) : this(
        exceptionCode.httpStatus,
        exceptionCode.serialCode(),
        exceptionCode.defaultMessage,
    )
}