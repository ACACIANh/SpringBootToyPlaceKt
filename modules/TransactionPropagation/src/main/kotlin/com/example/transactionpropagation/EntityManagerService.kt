package com.example.transactionpropagation

import jakarta.persistence.EntityManager
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * 기본 트랜잭션과 중첩 트랜잭션의 엔티티 매니저 정보를 비교하는 서비스
 */
@Service
class EntityManagerService(
    private val entityManager: EntityManager,
    private val nestedEntityManagerService: NestedEntityManagerService,
) {
    /**
     * 기본 트랜잭션(REQUIRED)을 사용하여 엔티티 매니저 정보를 수집하고
     * 중첩 트랜잭션(REQUIRES_NEW)을 호출하여 두 트랜잭션의 정보를 비교
     */
    @Transactional
    fun executeWithDefaultTransaction(): TransactionResultDto {

        val session = entityManager.unwrap(SharedSessionContractImplementor::class.java)
        val defaultEntityManagerInfo = EntityManagerInfoDto(
            entityManagerId = System.identityHashCode(session),
            prefix = Propagation.REQUIRED.name,
            isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive(),
            transactionName = TransactionSynchronizationManager.getCurrentTransactionName(),
            isolationLevel = TransactionSynchronizationManager.getCurrentTransactionIsolationLevel(),
        )

        val nestedEntityManagerInfo = nestedEntityManagerService.executeWithRequiresNewTransaction()

        return TransactionResultDto(
            defaultEntityManager = defaultEntityManagerInfo,
            nestedEntityManager = nestedEntityManagerInfo,
        )
    }
}

/**
 * REQUIRES_NEW 전파 방식을 사용하는 중첩 트랜잭션 서비스
 */
@Service
class NestedEntityManagerService(
    private val entityManager: EntityManager,
) {

    /**
     * REQUIRES_NEW 전파 방식으로 새로운 트랜잭션을 생성하고
     * 해당 트랜잭션의 엔티티 매니저 정보를 반환
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun executeWithRequiresNewTransaction(): EntityManagerInfoDto {
        val session = entityManager.unwrap(SharedSessionContractImplementor::class.java)
        return EntityManagerInfoDto(
            entityManagerId = System.identityHashCode(session),
            prefix = Propagation.REQUIRES_NEW.name,
            isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive(),
            transactionName = TransactionSynchronizationManager.getCurrentTransactionName(),
            isolationLevel = TransactionSynchronizationManager.getCurrentTransactionIsolationLevel()
                ?: TransactionDefinition.ISOLATION_DEFAULT,
        )
    }
}
