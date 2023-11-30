package com.bank.domain;

import java.util.UUID;

public record DepositRejected(UUID accountId, int i) implements Event {
}
