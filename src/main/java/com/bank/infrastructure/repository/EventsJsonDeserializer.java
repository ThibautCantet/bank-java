package com.bank.infrastructure.repository;

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

public class EventsJsonDeserializer extends JsonDeserializer<Events> {
    @Override
    public Events deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        List<Event> events = new ArrayList<>();
        for (JsonNode current : node) {
            var id = UUID.fromString(current.get("id").asText());
            String type = current.get("type").asText().split("domain.")[1];
            switch (type) {
                case "AccountCreated" -> events.add(new AccountCreated(id));
                case "AmountDeposited" -> {
                    int amount = current.get("amount").asInt();
                    events.add(new AmountDeposited(id, amount));
                }
                case "AmountWithdrawn" -> {
                    int amount = current.get("amount").asInt();
                    events.add(new AmountWithdrawn(id, amount));
                }
                case "WithdrawRejected" -> {
                    int amount = current.get("amount").asInt();
                    events.add(new WithdrawRejected(id, amount));
                }
                case "DepositRejected" -> {
                    int amount = current.get("amount").asInt();
                    events.add(new DepositRejected(id, amount));
                }
            }
        }
        return new Events(events);
        //throw new RuntimeException("Couldn't find a type to deserialize!");
    }
}
