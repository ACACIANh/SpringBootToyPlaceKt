package com.example.awssqs

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController(
    private val simpleProducer: SimpleProducer,
) {
    @PostMapping("/sqs/send")
    fun send(
        @RequestBody request: SimpleRequest,
    ): ResponseEntity<Any> {
        simpleProducer.produce(request)
        return ResponseEntity.ok().build()
    }
}