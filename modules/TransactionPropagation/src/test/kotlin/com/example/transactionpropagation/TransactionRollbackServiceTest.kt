package com.example.transactionpropagation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

private val log = KotlinLogging.logger { }

@SpringBootTest
class TransactionRollbackServiceTest(
    private val transactionRollbackService: TransactionRollbackService,
    private val productRepository: ProductRepository
) : FunSpec({

    beforeTest {
        // 각 테스트 전에 데이터베이스 초기화
        productRepository.deleteAll()
    }

    test("부모 메소드에서 예외가 처리되면 부모 트랜잭션은 롤백되지 않아야 함") {
        // 이 테스트를 위한 고유한 상품 이름 생성
        val productName = "test-${UUID.randomUUID()}"

        // try-catch가 있는 메소드 실행
        val result = transactionRollbackService.executeWithTryCatch(productName)

        // 결과 검증
        result shouldBe true

        // 부모 상품이 저장되었는지 확인 (부모 트랜잭션이 롤백되지 않음)
        val parentProducts = productRepository.findAll().filter { it.name == "Parent-$productName" }
        log.info { "[DEBUG_LOG] 찾은 부모 상품: $parentProducts" }
        parentProducts.size shouldBe 1

        // 중첩 상품이 저장되지 않았는지 확인 (중첩 트랜잭션이 롤백됨)
        val nestedProducts = productRepository.findAll().filter { it.name == "Nested-$productName" }
        log.info { "[DEBUG_LOG] 찾은 중첩 상품: $nestedProducts" }
        nestedProducts.size shouldBe 0
    }

    test("예외가 처리되지 않으면 두 트랜잭션 모두 롤백되어야 함") {
        // 이 테스트를 위한 고유한 상품 이름 생성
        val productName = "test-${UUID.randomUUID()}"

        try {
            // try-catch 없는 메소드 실행
            transactionRollbackService.executeWithoutTryCatch(productName)

            // 이 라인은 실행되지 않아야 함
            error("예외가 발생해야 합니다")
        } catch (e: Exception) {
            // 예상된 예외
            log.info { "[DEBUG_LOG] 예상된 예외 발생: ${e.message}" }
        }

        // 부모 상품이 저장되지 않았는지 확인 (부모 트랜잭션이 롤백됨)
        val parentProducts = productRepository.findAll().filter { it.name == "Parent-$productName" }
        log.info { "[DEBUG_LOG] 찾은 부모 상품: $parentProducts" }
        parentProducts.size shouldBe 0

        // 중첩 상품이 저장되지 않았는지 확인 (중첩 트랜잭션이 롤백됨)
        val nestedProducts = productRepository.findAll().filter { it.name == "Nested-$productName" }
        log.info { "[DEBUG_LOG] 찾은 중첩 상품: $nestedProducts" }
        nestedProducts.size shouldBe 0
    }
})
