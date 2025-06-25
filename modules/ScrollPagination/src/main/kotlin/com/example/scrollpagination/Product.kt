package com.example.scrollpagination

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

/**
 * 상품 정보를 나타내는 엔티티 클래스
 *
 * 데이터베이스에 저장되는 상품의 기본 정보를 포함합니다.
 */
@Entity
class Product(
    /**
     * 상품 이름
     */
    var name: String,

    /**
     * 상품 가격
     */
    var price: Int,

    /**
     * 상품 생성 시간 (기본값: 현재 시간)
     */
    var createdAt: LocalDateTime = LocalDateTime.now(),

    /**
     * 상품 고유 식별자 (자동 생성)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
