package com.example.transactionpropagation

import jakarta.persistence.EntityManager
import mu.KotlinLogging
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

private val log = KotlinLogging.logger { }

@Service
class EntityManagerService(
    private val entityManager: EntityManager,
    private val nestedEntityManagerService: NestedEntityManagerService,
) {
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

@Service
class NestedEntityManagerService(
    private val entityManager: EntityManager,
) {

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
