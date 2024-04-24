package com.example.simplevalidator

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Negative
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController {

    @GetMapping("/")
    fun index() = "Hello World!"

    @GetMapping("/ping")
    fun ping() = MyData("pong")

    @PostMapping("/valid")
    fun exception(@RequestBody @Valid request: ValidatedData) = "validation all pass"
}

data class MyData(val name: String)

data class ValidatedData(
    val notNullString: String,
    @field:NotNull
    val nullString: String?,
    @field:NotBlank
    val blankString: String?,
    @field:Negative @field:NotNull
    val negative: Int?,
    @field:Email @field:NotNull
    val email: String?,
)