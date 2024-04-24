package com.example.simplevalidator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController {

    @GetMapping("/")
    fun index() = "Hello World!"

    @GetMapping("/ping")
    fun ping() = "pong"
}