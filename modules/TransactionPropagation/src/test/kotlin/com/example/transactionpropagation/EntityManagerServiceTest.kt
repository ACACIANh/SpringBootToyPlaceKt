package com.example.transactionpropagation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import mu.KotlinLogging
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

private val log = KotlinLogging.logger { }

@SpringBootTest
class EntityManagerServiceTest(
    private val entityManagerService: EntityManagerService,
) : FunSpec(
    {
        test("REQUIRES_NEW 에서 사용하는 entity manager는 새로운 트랜잭션을 사용한다") {
            // 실행
            val result = entityManagerService.executeWithDefaultTransaction()

            // 결과
            log.info { "result: $result" }

            // 두 트랜잭션이 모두 활성화되어 있는지 확인
            result.defaultEntityManager.isTransactionActive shouldBe true
            result.nestedEntityManager.isTransactionActive shouldBe true

            // 트랜잭션 이름이 다른지 확인 (서로 다른 트랜잭션임을 나타냄)
            result.defaultEntityManager.transactionName shouldNotBe result.nestedEntityManager.transactionName

            // 엔티티 매니저 ID가 다른지 확인 (서로 다른 엔티티 매니저임을 나타냄)
            result.defaultEntityManager.entityManagerId shouldNotBe result.nestedEntityManager.entityManagerId
        }
    },
)
