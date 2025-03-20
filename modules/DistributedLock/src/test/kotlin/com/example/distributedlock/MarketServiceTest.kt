package com.example.distributedlock

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max

private val log = KotlinLogging.logger { }

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MarketServiceTest(
    val marketRepository: MarketRepository,
    val marketService: MarketService,
) : StringSpec({

    "마켓 생성 후 여러 스레드에서 동시에 티켓 구매하기" {
        val ticketCount = 1000
        val savedMarket = marketRepository.save(Market(ticketCount))

        val threadCount = 1010
        val executorService = Executors.newFixedThreadPool(threadCount)
        val latch = CountDownLatch(threadCount)

        val successCount = AtomicInteger()
        val failCount = AtomicInteger()

        repeat(threadCount) {
            executorService.submit {
                try {
                    marketService.sellTicket(savedMarket.id!!)
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    log.info { "구매 실패 이유: ${e.message}" }
                    failCount.incrementAndGet()
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        val updatedMarket = marketRepository.findById(savedMarket.id!!).get()
        log.info { "남은 티켓 수 : ${updatedMarket.ticketCount}" }

        (successCount.get() + failCount.get()) shouldBe threadCount

        log.info { "성공횟수 : ${successCount.get()}" }
        log.info { "실패횟수 : ${failCount.get()}" }
        log.info { "기댓값 : ${ticketCount - (successCount.get())}" }

        updatedMarket.ticketCount shouldBe max(ticketCount - (successCount.get()), 0)
    }
})
