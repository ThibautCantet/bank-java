package com.bank.domain;

import java.util.List;
import java.util.UUID;

public class Account {
    public Account(UUID id, List<Event> events) {

    }

    public Account(UUID id) {
    }

    public void deposit(int amount) {

    }

    public void withdraw(int amount) {

    }

    public float getCurrentBalance() {
        return 0;
    }

    public List<Event> getEvents() {
        return null;
    }

    public UUID getId() {
        return null;
    }
}
