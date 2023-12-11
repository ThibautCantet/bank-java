package com.bank.infrastructure;

import java.util.List;
import java.util.UUID;

import com.bank.domain.Account;
import com.bank.domain.AccountCreated;
import com.bank.domain.AmountDeposited;
import com.bank.domain.AmountWithdrawn;
import com.bank.domain.DepositRejected;
import com.bank.domain.WithdrawRejected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RedisEventStoreTest {

    private final UUID ACCOUNT_ID = UUID.fromString("00000000-000-0000-0000-000000000001");
    private RedisEventStore redisEventStore;

    @Autowired
    private RedisTemplate<UUID, Events> redisTemplate;

    @BeforeEach
    void setUp() {
        redisEventStore = new RedisEventStore(redisTemplate);
    }

    @Test
    void add_should_save_events_for_a_given_accountId() {
        var account = new Account(List.of(new AccountCreated(ACCOUNT_ID)));

        account.deposit(100);
        account.withdraw(10);
        account.withdraw(200);
        account.deposit(-10);

        redisEventStore.save(account);

        Events events = redisTemplate.opsForValue().get(ACCOUNT_ID);
        assertThat(events).isNotNull();
        assertThat(events.values()).containsExactly(new AmountDeposited(100), new AmountWithdrawn(10), new WithdrawRejected(200), new DepositRejected(-10));
    }

    @Test
    void find_should_return_initialized_account() {
        redisTemplate.opsForValue().set(ACCOUNT_ID, new Events(List.of(new AccountCreated(ACCOUNT_ID))));

        Account foundAccount = redisEventStore.find(ACCOUNT_ID);

        assertThat(foundAccount).isNotNull()
                .extracting(Account::getId)
                .isEqualTo(ACCOUNT_ID);
    }
}
