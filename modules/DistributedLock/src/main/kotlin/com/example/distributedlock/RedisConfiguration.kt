package com.example.distributedlock

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

private val log = KotlinLogging.logger { }

@Configuration
class RedisConfiguration {

    init {
        log.info { "RedisConfiguration init" }
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(REDIS_HOST, REDIS_PORT)
    }

    @Bean
    fun redisTemplate(): StringRedisTemplate {
        return StringRedisTemplate(redisConnectionFactory())
    }

    companion object {
        const val MAX_MEMORY = "maxmemory 128M"
        const val REDIS_HOST = "localhost"
        const val REDIS_PORT = 6379
    }
}
