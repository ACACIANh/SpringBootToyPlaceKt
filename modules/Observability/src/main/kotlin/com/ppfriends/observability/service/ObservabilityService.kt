package com.ppfriends.observability.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.random.Random

private val log = KotlinLogging.logger { }

@Service
class ObservabilityService {
    fun processData(id: String): String {
        log.debug { "데이터 처리 시작 - id: $id" }

        // Simulate some processing
        val processingTime = Random.nextLong(100, 500)
        Thread.sleep(processingTime)

        log.debug { "데이터 처리 완료 - 소요시간: ${processingTime}ms, id: $id" }

        val result = validateData(id)

        log.debug { "검증 결과: $result, id: $id" }

        return result
    }

    private fun validateData(id: String): String {
        log.trace { "데이터 검증 중 - id: $id" }

        return when {
            id.length < 3 -> {
                log.warn { "유효하지 않은 id 길이 - 길이: ${id.length}, id: $id" }
                "INVALID"
            }

            id.startsWith("err") -> {
                log.error { "에러 접두사 감지 - id: $id" }
                "ERROR"
            }

            else -> {
                log.debug { "데이터 검증 통과 - id: $id" }
                "VALID"
            }
        }
    }

    fun complexOperation(data: String): Map<String, Any> {
        log.info { "복잡한 작업 시작 - data: $data" }

        val step1 = performStep1(data)
        val step2 = performStep2(step1)
        val step3 = performStep3(step2)

        log.info { "복잡한 작업 완료" }

        return mapOf(
            "step1" to step1,
            "step2" to step2,
            "step3" to step3,
        )
    }

    private fun performStep1(data: String): String {
        log.debug { "1단계 수행 중 - data: $data" }
        Thread.sleep(100)
        return "$data-step1"
    }

    private fun performStep2(data: String): String {
        log.debug { "2단계 수행 중 - data: $data" }
        Thread.sleep(150)
        return "$data-step2"
    }

    private fun performStep3(data: String): String {
        log.debug { "3단계 수행 중 - data: $data" }
        Thread.sleep(200)
        return "$data-step3"
    }
}
