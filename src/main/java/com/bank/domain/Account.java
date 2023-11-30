package com.bank.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bank.es.DecisionFunction;
import com.bank.es.EvolutionFunction;

public class Account {
    private float balance;
    private UUID id;
    private final List<Event> events = new ArrayList<>();

    public Account(List<Event> events) {

        for (Event e: events) {
            switch (e) {
                case AccountCreated accountCreated -> apply(accountCreated);
                case AmountWithdrawn amountWithdrawn -> apply(amountWithdrawn);
                case AmountDeposited amountDeposited -> apply(amountDeposited);
                default -> {
                    return;
                }
            }
        }
    }

    @EvolutionFunction
    private void apply(AccountCreated accountCreated) {
        this.id = accountCreated.id();
    }

    @EvolutionFunction
    private void apply(AmountDeposited amountDeposited) {
        this.balance += amountDeposited.amount();
    }

    @EvolutionFunction
    private void apply(AmountWithdrawn amountWithdrawn) {
        this.balance -= amountWithdrawn.amount();
    }

    @DecisionFunction
    public void deposit(int amount) {
        if (amount < 0) {
            recordChange(new DepositRejected(amount));
        }
        else {
            var amountDeposited = new AmountDeposited(amount);
            apply(amountDeposited);
            recordChange(amountDeposited);
        }
    }

    @DecisionFunction
    public void withdraw(int amount) {
        if (amount > balance) {
            recordChange(new WithdrawRejected(amount));
        }
        else {
            var amountWithdrawn = new AmountWithdrawn(amount);
            apply(amountWithdrawn);
            recordChange(amountWithdrawn);
        }
    }

    private boolean recordChange(Event change) {
        return events.add(change);
    }

    public float getCurrentBalance() {
        return balance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public UUID getId() {
        return id;
    }
}
