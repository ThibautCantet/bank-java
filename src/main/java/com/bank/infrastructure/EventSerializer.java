package com.bank.infrastructure;

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

public class EventSerializer extends JsonSerializer<Events> {
    @Override
    public void serialize(Events value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        for (Event event : value.values()) {
            gen.writeStartObject();
            switch (event) {
                case AccountCreated accountCreated -> {
                    gen.writeStringField("id", String.valueOf(accountCreated.id()));
                    gen.writeStringField("type", accountCreated.getClass().getSimpleName());
                }
                case AmountDeposited amountDeposited -> {
                    gen.writeStringField("amount", String.valueOf(amountDeposited.amount()));
                    gen.writeStringField("type", amountDeposited.getClass().getSimpleName());
                }
                case AmountWithdrawn amountWithdrawn -> {
                    gen.writeStringField("amount", String.valueOf(amountWithdrawn.amount()));
                    gen.writeStringField("type", amountWithdrawn.getClass().getSimpleName());
                }
                case DepositRejected depositRejected -> {
                    gen.writeStringField("amount", String.valueOf(depositRejected.amount()));
                    gen.writeStringField("type", depositRejected.getClass().getSimpleName());
                }
                case WithdrawRejected withdrawRejected -> {
                    gen.writeStringField("amount", String.valueOf(withdrawRejected.amount()));
                    gen.writeStringField("type", withdrawRejected.getClass().getSimpleName());
                }
            }
            gen.writeEndObject();
        }

        gen.writeEndArray();
    }
}
