package com.bank.infrastructure;

import java.util.List;

import com.bank.domain.Event;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = EventDeserializer.class)
@JsonSerialize(using = EventSerializer.class)
public record Events(List<Event> values) {
}
