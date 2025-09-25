package com.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GracefulShutdownController {

    @GetMapping("/graceful-shutdown")
    fun gracefulShutdown(): String {
        Thread.sleep(5_000)
        return "Graceful shutdown is working!"
    }
}
