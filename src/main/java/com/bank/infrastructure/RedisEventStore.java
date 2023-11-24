package com.bank.infrastructure;

import java.util.List;
import java.util.UUID;

import com.bank.domain.Account;
import com.bank.domain.AmountWithdrawn;
import com.bank.domain.Event;
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

    }

    @Override
    public Account find(UUID id) {
        return null;
    }
}
