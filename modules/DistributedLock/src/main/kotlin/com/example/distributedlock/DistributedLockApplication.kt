package com.example.distributedlock

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
class DistributedLockApplication

fun main(args: Array<String>) {
    runApplication<DistributedLockApplication>(*args)
}

@Component
@Transactional
class AppStartupRunner(
    private val marketRepository: MarketRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        marketRepository.save(Market())
    }
}
