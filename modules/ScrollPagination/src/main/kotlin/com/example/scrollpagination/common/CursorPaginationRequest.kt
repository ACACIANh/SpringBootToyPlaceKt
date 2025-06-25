package com.example.scrollpagination.common

import org.springframework.data.domain.Limit

/**
 * 페이징 요청을 위한 공통 객체
 * @param cursor 다음 페이지 조회를 위한 커서 (null이면 첫 페이지)
 * @param limit 한 번에 조회할 데이터 개수 (기본값: 10)
 */
data class CursorPaginationRequest<C>(
    val cursor: C? = null,
    val limit: Int = 10,
) {
    init {
        require(limit > 0) { "limit은 0보다 커야 합니다." }
        require(limit <= 100) { "limit은 100을 초과할 수 없습니다." }
    }

    fun limit(): Limit {
        return Limit.of(limit)
    }
}
