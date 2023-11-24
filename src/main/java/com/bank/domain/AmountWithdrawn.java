package com.bank.domain;

import java.util.UUID;

public record AmountWithdrawn(UUID accountId, int amount) implements Event {
}
