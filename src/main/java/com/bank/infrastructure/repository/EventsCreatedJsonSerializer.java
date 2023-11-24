package com.bank.infrastructure.repository;

import java.io.IOException;

import com.bank.domain.AccountCreated;
import com.bank.domain.AmountDeposited;
import com.bank.domain.AmountWithdrawn;
import com.bank.domain.DepositRejected;
import com.bank.domain.Event;
import com.bank.domain.WithdrawRejected;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EventsCreatedJsonSerializer extends JsonSerializer<Events> {
    @Override
    public void serialize(Events events, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Event event: events.values()) {
            gen.writeStartObject();
            switch (event) {
                case AccountCreated accountCreated -> write(gen, accountCreated);
                case AmountWithdrawn amountWithdrawn -> write(gen, amountWithdrawn);
                case AmountDeposited amountDeposited -> write(gen, amountDeposited);
                case WithdrawRejected withdrawRejected -> write(gen, withdrawRejected);
                case DepositRejected depositRejected -> write(gen, depositRejected);
                default -> throw new IllegalStateException("Unexpected event: " + event);
            }
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }

    private void write(JsonGenerator gen, AccountCreated accountCreated) throws IOException {
        gen.writeStringField("type", accountCreated.getClass().getName());
        gen.writeStringField("id", accountCreated.id().toString());
    }

    private void write(JsonGenerator gen, AmountWithdrawn amountWithdrawn) throws IOException {
        gen.writeStringField("type", amountWithdrawn.getClass().getName());
        gen.writeStringField("id", amountWithdrawn.accountId().toString());
        gen.writeNumberField("amount", amountWithdrawn.amount());
    }

    private void write(JsonGenerator gen, AmountDeposited amountDeposited) throws IOException {
        gen.writeStringField("type", amountDeposited.getClass().getName());
        gen.writeStringField("id", amountDeposited.accountId().toString());
        gen.writeNumberField("amount", amountDeposited.amount());
    }

    private void write(JsonGenerator gen, WithdrawRejected withdrawRejected) throws IOException {
        gen.writeStringField("type", withdrawRejected.getClass().getName());
        gen.writeStringField("id", withdrawRejected.accountId().toString());
        gen.writeNumberField("amount", withdrawRejected.amount());
    }

    private void write(JsonGenerator gen, DepositRejected depositRejected) throws IOException {
        gen.writeStringField("type", depositRejected.getClass().getName());
        gen.writeStringField("id", depositRejected.accountId().toString());
        gen.writeNumberField("amount", depositRejected.amount());
    }
}
