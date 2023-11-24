package com.bank.infrastructure;

import java.util.List;

import com.bank.domain.Event;

public record Events(List<Event> values) {
}
