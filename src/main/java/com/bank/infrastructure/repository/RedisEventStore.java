package com.bank.infrastructure.repository;

import java.util.UUID;

import com.bank.domain.Account;
import com.bank.domain.EventStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisEventStore implements EventStore {
    private final RedisTemplate<String, Events> redisTemplate;

    public RedisEventStore(RedisTemplate<String, Events> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(Account account) {
        redisTemplate.opsForValue().set(account.getId().toString(), new Events(account.getEvents()));
    }

    @Override
    public Account find(UUID id) {
        Events events = redisTemplate.opsForValue().get(id.toString());

        return new Account(events.values());
    }
}
