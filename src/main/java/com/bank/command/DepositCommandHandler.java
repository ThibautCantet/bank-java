package com.bank.command;

import java.util.List;

import com.bank.domain.Account;
import com.bank.domain.Event;
import com.bank.domain.EventStore;

public class DepositCommandHandler {

    private final EventStore eventStore;

    public DepositCommandHandler(EventStore eventStore) {

        this.eventStore = eventStore;
    }

    public List<Event> handle(DepositCommand command) {
        Account account = eventStore.find(command.accountId());
        account.deposit(command.amount());
        eventStore.save(account);

        return account.getEvents();
    }
}
