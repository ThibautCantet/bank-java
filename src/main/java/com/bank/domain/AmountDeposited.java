package com.bank.domain;

import java.util.UUID;

public record AmountDeposited(UUID accountId, int amount) implements Event {
}
