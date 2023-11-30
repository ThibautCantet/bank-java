package com.bank.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import com.bank.command.DepositCommand;
import com.bank.command.DepositCommandHandler;
import com.bank.domain.AmountDeposited;
import com.bank.domain.Event;
import com.bank.domain.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountController.PATH)
public class AccountController {
    public static final String PATH = "/api/v1/account/";
    private final EventStore eventStore;

    public AccountController(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @GetMapping("{id}/balance/")
    public Balance get(@PathVariable("id") UUID id) {
        return new Balance(eventStore.find(id).getCurrentBalance());
    }

    @PostMapping("{id}/deposit/")
    public ResponseEntity<Void> deposit(@PathVariable("id") UUID id, @RequestBody Deposit deposit) {
        var depositCommandHandler = new DepositCommandHandler(eventStore);

        List<Event> events = depositCommandHandler.handle(new DepositCommand(id, deposit.amount()));

        if (events.stream().anyMatch(e -> e instanceof AmountDeposited)) {
            return ResponseEntity.noContent().build();
        }
        return null;
    }
}
