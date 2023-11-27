package com.bank.domain;

import java.util.UUID;

public interface EventStore {
    void save(Account account);

    Account find(UUID id);
}
