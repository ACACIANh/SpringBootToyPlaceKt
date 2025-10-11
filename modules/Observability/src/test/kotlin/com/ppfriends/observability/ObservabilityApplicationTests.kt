package com.ppfriends.observability

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

private val log = KotlinLogging.logger { }

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ObservabilityApplicationTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun contextLoads() {
    }

    @Test
    fun `모든 API 엔드포인트 호출 테스트`() {
        log.info { "=== Observability 테스트 시작 ===" }

        // 1. Hello 엔드포인트 호출
        log.info { "1. Hello 엔드포인트 호출 중..." }
        val helloResponse = restTemplate.getForEntity(
            "http://localhost:$port/api/observability/hello",
            Map::class.java,
        )
        log.info { "Hello 응답: ${helloResponse.body}" }
        assert(helloResponse.statusCode == HttpStatus.OK)

        Thread.sleep(500) // 로그 수집 대기

        // 2. Process 엔드포인트 호출 (정상 케이스)
        log.info { "2. Process 엔드포인트 호출 중 - 정상 케이스" }
        val processResponse = restTemplate.getForEntity(
            "http://localhost:$port/api/observability/process/test123",
            Map::class.java,
        )
        log.info { "Process 응답: ${processResponse.body}" }
        assert(processResponse.statusCode == HttpStatus.OK)

        Thread.sleep(500)

        // 3. Process 엔드포인트 호출 (짧은 ID - INVALID 케이스)
        log.info { "3. Process 엔드포인트 호출 중 - INVALID 케이스" }
        val invalidResponse = restTemplate.getForEntity(
            "http://localhost:$port/api/observability/process/ab",
            Map::class.java,
        )
        log.info { "Invalid 응답: ${invalidResponse.body}" }
        assert(invalidResponse.statusCode == HttpStatus.OK)

        Thread.sleep(500)

        // 4. Process 엔드포인트 호출 (에러 접두사 - ERROR 케이스)
        log.info { "4. Process 엔드포인트 호출 중 - ERROR 케이스" }
        val errorPrefixResponse = restTemplate.getForEntity(
            "http://localhost:$port/api/observability/process/err123",
            Map::class.java,
        )
        log.info { "Error prefix 응답: ${errorPrefixResponse.body}" }
        assert(errorPrefixResponse.statusCode == HttpStatus.OK)

        Thread.sleep(500)

        // 5. Slow 엔드포인트 호출
        log.info { "5. Slow 엔드포인트 호출 중..." }
        val slowResponse = restTemplate.getForEntity(
            "http://localhost:$port/api/observability/slow",
            Map::class.java,
        )
        log.info { "Slow 응답: ${slowResponse.body}" }
        assert(slowResponse.statusCode == HttpStatus.OK)

        Thread.sleep(500)

        // 6. Error 엔드포인트 호출 (예외 발생)
        log.info { "6. Error 엔드포인트 호출 중..." }
        try {
            restTemplate.getForEntity(
                "http://localhost:$port/api/observability/error",
                Map::class.java,
            )
        } catch (e: Exception) {
            log.warn { "예상된 에러 발생: ${e.message}" }
        }

        Thread.sleep(1000) // 모든 로그/메트릭이 전송될 시간 대기

        log.info { "=== Observability 테스트 완료 ===" }
        log.info { "Grafana에서 확인: http://localhost:3000" }
        log.info { "- Prometheus: {app=\"Observability\"}" }
        log.info { "- Loki: {app=\"Observability\"}" }
        log.info { "- Tempo: Service Name으로 검색" }
    }

    @Test
    fun `여러 요청을 연속으로 생성하여 트레이스 시각화`() {
        log.info { "=== 연속 요청 테스트 시작 ===" }

        repeat(10) { index ->
            log.info { "요청 #${index + 1} 시작" }

            // 다양한 ID로 요청
            val ids = listOf("valid123", "ab", "err999", "test456", "short")
            val randomId = ids.random()

            restTemplate.getForEntity(
                "http://localhost:$port/api/observability/process/$randomId",
                Map::class.java,
            )

            Thread.sleep(200) // 요청 간 간격
        }

        log.info { "=== 연속 요청 테스트 완료 ===" }
        log.info { "Tempo에서 여러 트레이스를 확인할 수 있습니다" }

        Thread.sleep(2000) // 데이터 전송 대기
    }
}
