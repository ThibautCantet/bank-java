package com.bank.infrastructure.repository;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisCacheConfig {

    @Bean
    RedisTemplate<String, Events> eventsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        var redisTemplate = new RedisTemplate<String, Events>();
        redisTemplate.setKeySerializer(RedisSerializer.java(UUID.class.getClassLoader()));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Events.class));
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }
}
