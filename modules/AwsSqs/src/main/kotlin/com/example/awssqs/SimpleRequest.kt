package com.example.awssqs

data class SimpleRequest(
    val destinationQueueName: String,
    val data: Human, // 필요에 따라 type 변경
)

data class Human(
    val name: String,
    val age: Int,
)
