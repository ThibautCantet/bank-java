package com.bank.domain;

public interface EventStore {
    void save(Account account);
}
