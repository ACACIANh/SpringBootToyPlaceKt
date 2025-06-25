package com.example.scrollpagination

import org.springframework.data.domain.Limit
import org.springframework.data.domain.ScrollPosition
import org.springframework.data.domain.Window
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 상품 데이터에 접근하기 위한 리포지토리 인터페이스
 *
 * 스크롤 기반 페이지네이션을 지원하는 다양한 쿼리 메소드를 제공합니다.
 */
interface ProductRepository : JpaRepository<Product, Long> {

    /**
     * ID 기준으로 내림차순 정렬된 모든 상품을 스크롤 페이지네이션으로 조회
     *
     * @param scrollPosition 스크롤 위치 정보 (ID 기준)
     * @param limit 조회할 데이터 개수 제한
     * @return 페이지네이션된 상품 목록
     */
    fun findByOrderByIdDesc(scrollPosition: ScrollPosition, limit: Limit): Window<Product>
}
