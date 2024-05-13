package com.example.fcm.endpoint

import com.example.fcm.core.FcmMessageParser
import com.example.fcm.core.FcmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FcmSimpleController(
    private val fcmMessageParser: FcmMessageParser, // parser 분리
    private val fcmService: FcmService,
) {

    @PostMapping("/push")
    fun push(@RequestBody request: PushRequest): ResponseEntity<Any> {
        val message = fcmMessageParser.parse(request)
        fcmService.push(message)
        return ResponseEntity.ok().build()
    }
}