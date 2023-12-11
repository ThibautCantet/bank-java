package com.bank.infrastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bank.domain.AccountCreated;
import com.bank.domain.AmountDeposited;
import com.bank.domain.AmountWithdrawn;
import com.bank.domain.DepositRejected;
import com.bank.domain.Event;
import com.bank.domain.WithdrawRejected;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class EventDeserializer extends JsonDeserializer<Events> {
    @Override
    public Events deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        List<Event> events = new ArrayList<>();

        Events result = new Events(events);

        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        for (JsonNode current : node) {
            Event e;
            switch (current.get("type").asText()) {
                case "AccountCreated" ->  e = new AccountCreated(UUID.fromString(current.get("id").textValue()));
                case "AmountDeposited" -> e = new AmountDeposited(current.get("amount").asInt());
                case "AmountWithdrawn" -> e = new AmountWithdrawn(current.get("amount").asInt());
                case "DepositRejected" -> e = new DepositRejected(current.get("amount").asInt());
                case "WithdrawRejected" -> e = new WithdrawRejected(current.get("amount").asInt());
                default -> throw new IllegalStateException("Unexpected value: " + current.get("type"));
            }
            events.add(e);
        }

        return result;
    }
}
