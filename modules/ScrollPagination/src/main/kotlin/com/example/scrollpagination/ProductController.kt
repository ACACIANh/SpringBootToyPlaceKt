package com.example.scrollpagination

import com.example.scrollpagination.common.CursorPaginationRequest
import com.example.scrollpagination.common.PaginationResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 상품 관련 API를 처리하는 컨트롤러
 *
 * 상품 생성 및 다양한 조건으로 상품을 조회하는 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    /**
     * 새로운 상품을 생성하는 엔드포인트
     *
     * @param request 상품 생성 요청 데이터
     * @return 생성된 상품 정보
     */
    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): ResponseEntity<Product> {
        val product = productService.createProduct(request.name, request.price)
        return ResponseEntity.ok(product)
    }

    /**
     * 모든 상품을 페이지네이션하여 조회하는 엔드포인트
     *
     * @param cursor 페이지네이션 요청 정보
     * @return 페이지네이션된 상품 목록
     */
    @GetMapping
    fun getProducts(
        @ModelAttribute cursor: CursorPaginationRequest<Long>,
    ): ResponseEntity<PaginationResult<Product>> {
        val result = productService.getProducts(cursor.cursor, cursor.limit())
        return ResponseEntity.ok(result)
    }

}

/**
 * 상품 생성 요청을 위한 데이터 클래스
 *
 * @param name 생성할 상품의 이름
 * @param price 생성할 상품의 가격
 */
data class CreateProductRequest(
    val name: String,
    val price: Int,
)
