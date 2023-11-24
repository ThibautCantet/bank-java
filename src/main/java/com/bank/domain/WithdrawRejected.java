package com.bank.domain;

import java.util.UUID;

public record WithdrawRejected(UUID accountId, int amount) implements Event {
}
