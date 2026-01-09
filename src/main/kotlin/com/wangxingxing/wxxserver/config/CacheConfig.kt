package com.wangxingxing.wxxserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfig {

    /**
     * Redis 缓存管理器
     * 仅在配置了 spring.cache.type=redis 时启用
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = ["spring.cache.type"], havingValue = "redis", matchIfMissing = false)
    fun redisCacheManager(
        connectionFactory: RedisConnectionFactory,
        @Value("\${wxx.cache.default-ttl-seconds:600}") defaultTtlSeconds: Long
    ): CacheManager {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = GenericJackson2JsonRedisSerializer()
        val config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(defaultTtlSeconds))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build()
    }

    /**
     * 简单内存缓存管理器（开发环境降级方案）
     * 当 Redis 不可用时使用
     */
    @Bean
    @ConditionalOnProperty(name = ["spring.cache.type"], havingValue = "simple", matchIfMissing = true)
    fun simpleCacheManager(): CacheManager {
        return ConcurrentMapCacheManager()
    }
}

