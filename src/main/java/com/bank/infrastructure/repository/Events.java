package com.bank.infrastructure.repository;

import java.util.List;

import com.bank.domain.Event;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = EventsJsonDeserializer.class)
@JsonSerialize(using = EventsCreatedJsonSerializer.class)
public record Events(List<Event> values) {
}
