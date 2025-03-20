package com.example.distributedlock

import mu.KotlinLogging
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private val log = KotlinLogging.logger { }

@Component
class DistributedLockAspectHolder(
    redissonClient: RedissonClient,
    transactionProcessor: DistributedLockTransactionProcessor
) {

    init {
        DistributedLockAspectHolder.redissonClient = redissonClient
        DistributedLockAspectHolder.transactionProcessor = transactionProcessor
    }

    companion object {
        lateinit var redissonClient: RedissonClient
            private set
        lateinit var transactionProcessor: DistributedLockTransactionProcessor
            private set
        const val REDISSON_LOCK_PREFIX = "LOCK:"

        fun <T> proceed(function: () -> T): T {
            return function()
        }

        fun <T> proceedWithTransaction(function: () -> T): T {
            return transactionProcessor.proceed(function)
        }
    }
}

// 람다 중첩 구조로 가져갈수 있는지 확인하기
fun <T> distributedLock(
    key: String,
    withTransaction: Boolean = false,
    waitDuration: Duration = 3.seconds,
    leaseDuration: Duration = 3.seconds,
    function: () -> T,
): T {
    val rLock: RLock = (DistributedLockAspectHolder.REDISSON_LOCK_PREFIX + key)
        .let { DistributedLockAspectHolder.redissonClient.getLock(it) }

    try {
        log.debug { "(1)lock 획득 시도 key[$key]" }
        val available: Boolean = rLock.tryLock(
            waitDuration.inWholeSeconds,
            leaseDuration.inWholeSeconds,
            TimeUnit.SECONDS,
        )
        check(available) {
            log.error { "(2)lock 획득 실패 key[$key]" }
            throw IllegalStateException("2:lock 획득 실패 key[$key]")
        }
        log.debug { "(3)lock 획득 성공 key[$key]" }
        if (withTransaction) {
            return DistributedLockAspectHolder.proceedWithTransaction(function)
        }
        return DistributedLockAspectHolder.proceed(function)
    } finally {
        try {
            rLock.unlock()
            log.debug { "(4)lock 반환 성공 key[$key]" }
        } catch (e: IllegalMonitorStateException) {
            log.debug { "(4)lock 반환 실패 key[$key]" }
        }
    }
}
