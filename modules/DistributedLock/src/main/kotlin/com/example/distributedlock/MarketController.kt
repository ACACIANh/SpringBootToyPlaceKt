package com.example.distributedlock

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/markets")
class MarketController(
    private val marketService: MarketService,
) {
    @GetMapping
    fun findAllMarkets(
    ) = marketService.findAllMarkets()

    @GetMapping("/{id}")
    fun findMarket(
        @PathVariable id: Long,
    ) = marketService.findMarket(id)

    @PostMapping("/{id}")
    fun refillTicket(
        @PathVariable id: Long,
    ) {
        marketService.refillTicket(id)
    }

    @DeleteMapping("/{id}/tickets")
    fun orderTickets(
        @PathVariable id: Long,
    ) {
        marketService.sellTicket(id)
    }
}

