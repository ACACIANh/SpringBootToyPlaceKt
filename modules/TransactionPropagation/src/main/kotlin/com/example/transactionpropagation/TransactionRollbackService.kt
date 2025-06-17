package com.example.transactionpropagation

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger { }

/**
 * 예외 처리를 통한 트랜잭션 롤백 동작을 테스트하기 위한 서비스
 */
@Service
class TransactionRollbackService(
    private val productRepository: ProductRepository,
    private val nestedTransactionService: NestedTransactionService
) {
    /**
     * REQUIRES_NEW를 사용하는 중첩 메소드를 호출하고
     * try-catch로 예외를 처리하는 부모 트랜잭션 메소드
     */
    @Transactional
    fun executeWithTryCatch(productName: String): Boolean {
        // 부모 트랜잭션에서 상품 생성
        val parentProduct = Product(name = "Parent-$productName", price = 1000)
        productRepository.save(parentProduct)
        log.info { "부모 상품 저장됨: $parentProduct" }

        try {
            // 예외를 발생시키는 중첩 메소드 호출
            nestedTransactionService.executeWithException(productName)
        } catch (e: Exception) {
            log.info { "부모 메소드에서 예외 처리됨: ${e.message}" }
        }

        // 부모 트랜잭션이 여전히 활성 상태(롤백되지 않음)인 경우 true 반환
        return true
    }

    /**
     * REQUIRES_NEW를 사용하는 중첩 메소드를 호출하지만
     * 예외를 처리하지 않는 부모 트랜잭션 메소드
     */
    @Transactional
    fun executeWithoutTryCatch(productName: String): Boolean {
        // 부모 트랜잭션에서 상품 생성
        val parentProduct = Product(name = "Parent-$productName", price = 1000)
        productRepository.save(parentProduct)
        log.info { "부모 상품 저장됨: $parentProduct" }

        // 예외를 발생시키는 중첩 메소드 호출
        // 이 예외는 호출자에게 전파됨
        nestedTransactionService.executeWithException(productName)

        // 예외가 발생하면 이 라인은 실행되지 않음
        return true
    }
}

/**
 * REQUIRES_NEW 전파 방식을 사용하는 메소드가 있는 서비스
 */
@Service
class NestedTransactionService(
    private val productRepository: ProductRepository
) {
    /**
     * 상품 저장 후 예외를 발생시키는 REQUIRES_NEW 전파 방식의 메소드
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun executeWithException(productName: String) {
        // 중첩 트랜잭션에서 상품 생성
        val nestedProduct = Product(name = "Nested-$productName", price = 500)
        productRepository.save(nestedProduct)
        log.info { "중첩 상품 저장됨: $nestedProduct" }

        // 이 트랜잭션의 롤백을 트리거하기 위한 예외 발생
        throw RuntimeException("중첩 트랜잭션에서 의도적으로 발생시킨 예외")
    }
}
