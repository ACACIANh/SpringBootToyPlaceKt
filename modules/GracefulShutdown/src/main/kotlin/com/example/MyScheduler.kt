package com.example

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

private val log = KotlinLogging.logger { }

@Component
class MyScheduler {


    @Scheduled(fixedDelay = 10_000, timeUnit = TimeUnit.MILLISECONDS)
    fun run() {
        log.info("Scheduler is start...")
        Thread.sleep(5_000)
        log.info("Scheduler is end...")
    }
}
