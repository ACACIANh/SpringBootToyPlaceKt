package com.example.exceptionstrategy.exception

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    override val httpStatus: HttpStatus,
    override val serial: String,
    override val code: Int,
    override val defaultMessage: String
) : IException {
    //@formatter:off

    // common
    INVALID_INPUT       (HttpStatus.BAD_REQUEST,           "COMMON", 1, "Invalid input provided"),
    RESOURCE_NOT_FOUND  (HttpStatus.NOT_FOUND,             "COMMON", 2, "Resource with ID %s not found"),
    INTERNAL_ERROR      (HttpStatus.INTERNAL_SERVER_ERROR, "COMMON", 3, "Internal server error"),

    // domain A
    DUPLICATE_ENTRY     (HttpStatus.CONFLICT,              "DOMAIN_A", 1, "Duplicate entry for '%s'"),

    // domain B
    INVALID_OPERATION   (HttpStatus.BAD_REQUEST,           "DOMAIN_B", 1, "Invalid operation: '%s', '%s'"),

    ;
    //@formatter:on

    override fun formatMessage(vararg args: Any): String {
        if (args.isEmpty()) return defaultMessage
        return String.format(defaultMessage, *args)
    }
}