package com.bank.domain;

public record DepositRejected(int amount) implements Event {
}
