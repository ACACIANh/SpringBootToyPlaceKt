package com.example.distributedlock

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

/**
 * package 를 없앤 simpleName 을 의도해도 작동하지않아 임시로 substringAfterLast 를 사용했지만 메모리관점으로 좋지 않을것으로 보임
 */
inline fun <reified T, ID> CrudRepository<T, ID>.findByIdOrThrow(
    id: ID,
    e: Exception = IllegalStateException(
        "${T::class.java.name.substringAfterLast('.')} Entity 를 찾을 수 없습니다. id[$id]",
    ),
): T = findByIdOrNull(id) ?: throw e

/**
 * findByIdOrThrow 와 같은 이슈 확인
 */
inline fun <reified T, ID> CrudRepository<T, ID>.findByIdExistThrow(
    id: ID,
    e: Exception = IllegalStateException(
        "${T::class.java.name.substringAfterLast('.')} Entity 를 찾을 수 없습니다. id[$id]",
    ),
): T? = findByIdOrNull(id)?.let { throw e }

interface MarketRepository : JpaRepository<Market, Long>
