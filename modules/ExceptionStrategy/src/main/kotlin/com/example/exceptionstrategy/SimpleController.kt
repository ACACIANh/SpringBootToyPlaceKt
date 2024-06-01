package com.example.exceptionstrategy

import com.example.exceptionstrategy.exception.ApplicationException
import com.example.exceptionstrategy.exception.ExceptionCode
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController {

    @GetMapping("/ping")
    fun ping(
        param1: String,
        param2: String,
    ) {
        if (param1 == "illegal") {
            throw ApplicationException.wrap(ExceptionCode.INTERNAL_ERROR, IllegalArgumentException("custom illegal"))
        }
        if (param1.isNotEmpty() && param2.isNotEmpty()) {
            throw ApplicationException.custom(ExceptionCode.INVALID_OPERATION, param1, param2)
        }
        "pong"
    }
}