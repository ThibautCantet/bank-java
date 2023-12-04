package com.bank.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bank.es.DecisionFunction;
import com.bank.es.EvolutionFunction;

public class Account {
    private UUID id;
    private int currentBalance;
    private final List<Event> events = new ArrayList<>();

    public Account(List<Event> events) {
        for (Event event : events) {
            switch (event) {
                case AccountCreated accountCreated -> apply(accountCreated);
                case AmountDeposited amountDeposited -> apply(amountDeposited);
                case AmountWithdrawn amountWithdrawn -> apply(amountWithdrawn);
                case DepositRejected depositRejected -> apply(depositRejected);
                case WithdrawRejected withdrawRejected -> apply(withdrawRejected);
            }
        }
    }

    @DecisionFunction
    public void deposit(int amount) {
        Event event;
        if (amount < 0) {
            event = new DepositRejected(amount);
        } else {
            event = new AmountDeposited(amount);
            apply((AmountDeposited) event);
        }
        events.add(event);
    }

    @DecisionFunction
    public void withdraw(int amount) {
        Event event;
        if (amount < 0 || currentBalance < amount) {
            event = new WithdrawRejected(amount);
        } else {
            event = new AmountWithdrawn(amount);
            apply((AmountWithdrawn) event);
        }
        events.add(event);
    }

    @EvolutionFunction
    private void apply(AccountCreated accountCreated) {
        id = accountCreated.id();
    }

    @EvolutionFunction
    private void apply(AmountDeposited amountDeposited) {
        currentBalance += amountDeposited.amount();
    }

    @EvolutionFunction
    private void apply(AmountWithdrawn amountWithdrawn) {
        currentBalance -= amountWithdrawn.amount();
    }

    @EvolutionFunction
    private void apply(DepositRejected depositRejected) {
    }

    private void apply(WithdrawRejected withdrawRejected) {
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public UUID getId() {
        return id;
    }
}
