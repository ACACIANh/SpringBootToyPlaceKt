package com.example.scrollpagination

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Limit

@SpringBootTest
class ProductServiceTest(
    private val productService: ProductService,
    private val productRepository: ProductRepository,
) : FunSpec(
    {

        // 테스트 실행 전에 데이터베이스 초기화
        beforeTest {
            productRepository.deleteAll()
        }

        // 상품 생성 테스트
        test("상품 생성 테스트") {
            // given: 상품 정보 준비
            val name = "테스트 상품"
            val price = 10000

            // when: 상품 생성 실행
            val product = productService.createProduct(name, price)

            // then: 생성된 상품 검증
            product.name shouldBe name
            product.price shouldBe price
        }

        // 전체 상품 조회 테스트
        test("전체 상품 페이지네이션 조회 테스트") {
            // given: 테스트 데이터 준비
            val product1 = productService.createProduct("상품1", 1000)
            val product2 = productService.createProduct("상품2", 2000)
            val product3 = productService.createProduct("상품3", 3000)

            // when: 첫 페이지 조회 (2개 항목)
            val firstPage = productService.getProducts(null, Limit.of(2))

            // then: 첫 페이지 검증
            firstPage.content.size shouldBe 2
            firstPage.hasNext shouldBe true

            // when: 다음 페이지 조회
            val secondPage = productService.getProducts(firstPage.content.last().id, Limit.of(2))

            // then: 다음 페이지 검증
            secondPage.content.size shouldBe 1
            secondPage.hasNext shouldBe false
        }

        // 빈 결과 조회 테스트
        test("빈 결과 조회 테스트") {
            // when: 데이터가 없는 상태에서 조회
            val result = productService.getProducts(null, Limit.of(10))

            // then: 빈 결과 검증
            result.content.size shouldBe 0
            result.hasNext shouldBe false
        }

        // 최대 항목 수 조회 테스트
        test("최대 항목 수 조회 테스트") {
            // given: 테스트 데이터 준비 (10개 상품)
            repeat(10) { index ->
                productService.createProduct("대량 상품 ${index + 1}", (index + 1) * 1000)
            }

            // when: 최대 항목 수로 조회 (10개)
            val result = productService.getProducts(null, Limit.of(10))

            // then: 모든 항목이 조회되는지 검증
            result.content.size shouldBe 10
            result.hasNext shouldBe false
        }

        // 정렬 순서 테스트
        test("ID 기준 내림차순 정렬 테스트") {
            // given: 테스트 데이터 준비
            val product1 = productService.createProduct("첫번째 상품", 1000)
            val product2 = productService.createProduct("두번째 상품", 2000)
            val product3 = productService.createProduct("세번째 상품", 3000)

            // when: 전체 상품 조회
            val result = productService.getProducts(null, Limit.of(10))

            // then: ID 역순으로 정렬되었는지 검증
            result.content.size shouldBe 3
            // ID는 자동 증가하므로 마지막에 생성된 상품이 가장 큰 ID를 가짐
            result.content[0].name shouldBe "세번째 상품"
            result.content[1].name shouldBe "두번째 상품"
            result.content[2].name shouldBe "첫번째 상품"
        }

    },
)
