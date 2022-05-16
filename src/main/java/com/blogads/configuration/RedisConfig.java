package com.blogads.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory());
        // value serializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hash value serializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    //region cache
    private static final String PREFIX_CACHE_NAME = "COM.BLOGADS.";
    private static final long DEFAULT_CACHE_EXPIRATION = 30;

    @Bean
    public RedisCacheWriter redisCacheWriter() {
        return RedisCacheWriter.lockingRedisCacheWriter(connectionFactory());
    }

    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(DEFAULT_CACHE_EXPIRATION))
                .disableCachingNullValues()
                .prefixCacheNameWith(PREFIX_CACHE_NAME)
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );
    }

    @Bean
    public CacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> cacheNames = new HashMap<>();
//        cacheNames.put(POST_CACHE_NAME, defaultRedisCacheConfiguration().entryTtl(Duration.ofDays(3L)));
//        cacheNames.put("horse", defaultRedisCacheConfiguration().entryTtl(Duration.ofMinutes(10L)));
        return new RedisCacheManager(redisCacheWriter(), defaultRedisCacheConfiguration(), cacheNames);
    }

    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }
    //endregion
}
