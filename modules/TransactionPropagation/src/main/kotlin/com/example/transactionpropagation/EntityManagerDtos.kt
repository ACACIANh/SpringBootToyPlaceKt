package com.example.transactionpropagation

/**
 * 엔티티 매니저에 대한 정보를 나타내는 DTO
 */
data class EntityManagerInfoDto(
    val entityManagerId: Int,
    val prefix: String,
    val isTransactionActive: Boolean? = null,
    val transactionName: String? = null,
    val isolationLevel: Int? = null,
)

/**
 * 트랜잭션 실행 결과를 나타내는 DTO
 */
data class TransactionResultDto(
    val defaultEntityManager: EntityManagerInfoDto,
    val nestedEntityManager: EntityManagerInfoDto,
)
