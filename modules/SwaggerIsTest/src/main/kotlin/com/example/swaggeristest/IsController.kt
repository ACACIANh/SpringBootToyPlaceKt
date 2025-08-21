package com.example.swaggeristest

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/test")
@Tag(name = "Test API", description = "Swagger 테스트를 위한 API")
class IsController {

    @Operation(summary = "인사 메시지 조회", description = "간단한 인사 메시지를 반환합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageResponse::class),
                    ),
                ],
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "500", description = "서버 오류"),
        ],
    )
    @GetMapping("/hello")
    fun getHello(
        @Parameter(description = "이름", required = false, example = "홍길동")
        @RequestParam(required = false) name: String?,
    ): ResponseEntity<MessageResponse> {
        val message = if (name != null) {
            "안녕하세요, $name 님!"
        } else {
            "안녕하세요!"
        }
        return ResponseEntity.ok(MessageResponse(message))
    }
}

@Schema(description = "메시지 응답")
data class MessageResponse(
    @Schema(description = "메시지", example = "안녕하세요!")
    val message: String,
    @Schema(description = "타임스탬프", example = "2024-01-01T12:00:00")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "isXXX테스트", example = "true")
    @get:JsonProperty("isTest")
    val isTest: Boolean = true,
    @Schema(description = "isXXX테스트2", example = "true")
    val isTest2: Boolean = true,
)
