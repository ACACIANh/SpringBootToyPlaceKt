package com.example.scrollpagination

import com.example.scrollpagination.common.PaginationResult
import org.springframework.data.domain.Limit
import org.springframework.data.domain.ScrollPosition
import org.springframework.stereotype.Service

/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 클래스
 *
 * 상품 생성 및 다양한 조건으로 상품을 조회하는 기능을 제공합니다.
 */
@Service
class ProductService(private val productRepository: ProductRepository) {

    /**
     * 새로운 상품을 생성하는 메소드
     *
     * @param name 상품 이름
     * @param price 상품 가격
     * @return 생성된 상품 객체
     */
    fun createProduct(name: String, price: Int): Product {
        val product = Product(name = name, price = price)
        return productRepository.save(product)
    }

    /**
     * 모든 상품을 ID 기반 커서 페이지네이션으로 조회하는 메소드
     * 상품 ID를 기준으로 내림차순 정렬하여 조회합니다.
     *
     * @param cursor 페이지네이션 커서 (상품 ID, null이면 첫 페이지)
     * @param limit 한 페이지당 항목 수
     * @return 페이지네이션된 상품 목록
     */
    fun getProducts(cursor: Long?, limit: Limit): PaginationResult<Product> {
        val scrollPosition = createScrollPosition(cursor)
        val window = productRepository.findByOrderByIdDesc(scrollPosition, limit)
        return PaginationResult.from(window)
    }

    /**
     * 커서로부터 ScrollPosition 객체를 생성하는 헬퍼 메소드
     *
     * @param cursor 페이지네이션 커서 (상품 ID, null이면 첫 페이지)
     * @return 생성된 ScrollPosition 객체
     */
    private fun createScrollPosition(cursor: Long?): ScrollPosition {
        return if (cursor == null) {
            // 첫 페이지는 offset 기반으로 시작
            ScrollPosition.offset()
        } else {
            // 상품 ID를 기준으로 다음 페이지 조회
            // 내림차순 정렬이므로 주어진 ID보다 작은 값들을 조회
            ScrollPosition.forward(mapOf(Product::id.name to cursor))
        }
    }
}
