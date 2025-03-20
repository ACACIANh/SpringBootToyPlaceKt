package com.example.distributedlock

import com.example.distributedlock.RedisConfiguration.Companion.REDIS_HOST
import com.example.distributedlock.RedisConfiguration.Companion.REDIS_PORT
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import mu.KotlinLogging
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer
import redis.embedded.core.RedisServerBuilder

private val log = KotlinLogging.logger { }

@Configuration
class RedissonConfiguration {
    private lateinit var redisServer: RedisServer

    init {
        log.info { "RedissonConfiguration init" }
    }

    /**
     * RedisConfiguration 클래스에서 단독으로 init 블록이 실행되지 않은 버그로 해당위치에서 실행
     */
    @PostConstruct
    fun startRedis() {
        redisServer = RedisServerBuilder()
            .port(REDIS_PORT)
            .build()
        redisServer.start()
        log.info { "$REDIS_HOST:$REDIS_PORT Redis Server started" }
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = REDIS_ADDRESS
        return Redisson.create(config)
    }

    companion object {
        const val REDIS_ADDRESS = "redis://localhost:6379"
    }
}
