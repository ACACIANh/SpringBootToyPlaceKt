package com.example.distributedlock

import org.springframework.stereotype.Service

@Service
class MarketService(
    private val marketRepository: MarketRepository
) {
    fun findAllMarkets() = marketRepository.findAll()

    fun findMarket(
        marketId: Long,
    ) = marketRepository.findByIdOrThrow(marketId)

    fun refillTicket(
        marketId: Long,
        count: Int = 10,
    ) = distributedLock(key = "ticketMarket-$marketId", withTransaction = true) {
        val market = marketRepository.findByIdOrThrow(marketId)
        market.refillTickets(count)
    }

    fun sellTicket(
        marketId: Long,
    ) = distributedLock(key = "ticketMarket-$marketId", withTransaction = true) {
        val market = marketRepository.findByIdOrThrow(marketId)
        Thread.sleep(2)
        market.sellTicket()
    }
}
