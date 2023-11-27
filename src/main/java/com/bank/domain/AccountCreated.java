package com.bank.domain;

import java.util.UUID;

public record AccountCreated(UUID id) implements Event {
}
