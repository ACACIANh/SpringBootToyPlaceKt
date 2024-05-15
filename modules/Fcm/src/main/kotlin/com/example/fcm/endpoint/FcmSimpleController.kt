package com.example.fcm.endpoint

import com.example.fcm.core.FcmMessageParser
import com.example.fcm.core.FcmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FcmSimpleController(
    private val fcmSimpleMessageParser: FcmSimpleMessageParser,
    private val fcmService: FcmService,
) {

    @PostMapping("/push")
    fun push(@RequestBody request: FcmSimpleRequest): ResponseEntity<Any> {
        val message = fcmSimpleMessageParser.parse(request)
        fcmService.send(message)
        return ResponseEntity.ok().build()
    }
}