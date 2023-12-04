package com.bank.domain;

public sealed interface Event permits AccountCreated, AmountDeposited, AmountWithdrawn, DepositRejected, WithdrawRejected {
}
