package com.example.distributedlock

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import mu.KotlinLogging

private val log = KotlinLogging.logger { }

@Entity
class Market(
    var ticketCount: Int = 10,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun refillTickets(count: Int = 1) {
        ticketCount += count
    }

    fun sellTicket() {
        if (ticketCount <= 0) {
            throw IllegalStateException("티켓이 모두 판매되었습니다.")
        }
        ticketCount--
        log.info { "판매 성공 현재 티켓 수: $ticketCount" }
    }
}

