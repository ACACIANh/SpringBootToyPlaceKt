package com.ppfriends.observability.controller

import com.ppfriends.observability.service.ObservabilityService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger { }

@RestController
@RequestMapping("/api/observability")
class ObservabilityController(
    private val observabilityService: ObservabilityService,
) {

    @GetMapping("/hello")
    fun hello(): Any {
        log.info { "Hello 엔드포인트 호출됨" }
        return mapOf("message" to "Hello, Observability!")
    }

    @GetMapping("/process/{id}")
    fun process(@PathVariable id: String): Any {
        log.info { "처리 요청 시작 - id: $id" }

        val result = observabilityService.processData(id)

        log.info { "처리 요청 완료 - id: $id" }
        return mapOf(
            "id" to id,
            "result" to result,
            "status" to "success",
        )
    }

    @GetMapping("/error")
    fun simulateError(): Any {
        log.error { "에러 시뮬레이션 발생" }
        throw RuntimeException("This is a simulated error for observability testing")
    }

    @GetMapping("/slow")
    fun slowEndpoint(): Any {
        log.info { "느린 엔드포인트 호출됨" }
        Thread.sleep(2000) // Simulate slow operation
        log.info { "느린 엔드포인트 완료됨" }
        return mapOf("message" to "This was slow!")
    }
}
