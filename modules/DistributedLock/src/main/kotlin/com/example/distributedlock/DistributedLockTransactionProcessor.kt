package com.example.distributedlock

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class DistributedLockTransactionProcessor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> proceed(function: () -> T): T {
        return function()
    }
}
