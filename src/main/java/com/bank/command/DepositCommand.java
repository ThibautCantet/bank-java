package com.bank.command;

import java.util.UUID;

public record DepositCommand(UUID accountId, int amount) {
}
