package com.example.transactionpropagation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionPropagationApplication

fun main(args: Array<String>) {
    runApplication<TransactionPropagationApplication>(*args)
}
