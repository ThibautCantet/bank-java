package com.bank.domain;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void add(UUID accountId, List<Event> events);
}
