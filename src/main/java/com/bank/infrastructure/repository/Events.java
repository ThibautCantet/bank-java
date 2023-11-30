package com.bank.infrastructure.repository;

import java.util.List;

import com.bank.domain.Event;

public record Events(List<Event> values) {
}
