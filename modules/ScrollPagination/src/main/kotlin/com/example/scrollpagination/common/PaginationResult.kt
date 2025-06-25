package com.example.scrollpagination.common

import org.springframework.data.domain.Window

/**
 * 페이징 결과를 나타내는 데이터 클래스 T - Content 타입, C - 커서 타입
 * @param content 페이지네이션된 데이터 목록
 * @param hasNext 다음 페이지가 있는지 여부
 */
data class PaginationResult<T>(
    val content: List<T>,
    val hasNext: Boolean,
) {
    companion object {
        fun <T> from(window: Window<T>): PaginationResult<T> = PaginationResult(window.content, window.hasNext())
    }
}
