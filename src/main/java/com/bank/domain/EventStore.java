package com.bank.domain;

import java.util.List;

public interface EventStore {
    void add(List<Event> events);
}
