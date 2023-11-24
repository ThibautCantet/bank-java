package com.bank.domain;

public record WithdrawRejected(String reason) implements Event {
}
