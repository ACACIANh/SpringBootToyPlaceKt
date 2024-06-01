package com.example.exceptionstrategy.exception

import org.springframework.http.HttpStatus

interface IException {
    val httpStatus: HttpStatus
    val serial: String
    val code: Int
    val defaultMessage: String

    fun formatMessage(vararg args: Any): String {
        if (args.isEmpty()) return defaultMessage
        return String.format(defaultMessage, *args)
    }

    fun serialCode(): String {
        return "$serial:${String.format(CODE_SPACE, code)}"
    }

    companion object {
        private const val CODE_SPACE = "%04d"
    }
}
