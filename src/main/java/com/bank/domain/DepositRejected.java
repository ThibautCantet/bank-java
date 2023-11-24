package com.bank.domain;

public record DepositRejected(java.util.UUID accountId, int amount) implements Event {
}
