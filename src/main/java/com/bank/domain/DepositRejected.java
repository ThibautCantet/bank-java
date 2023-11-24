package com.bank.domain;

public record DepositRejected(String reason) implements Event {
}
