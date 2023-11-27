package com.bank.command;

import java.util.List;
import java.util.UUID;

import com.bank.domain.Account;
import com.bank.domain.AccountCreated;
import com.bank.domain.AmountDeposited;
import com.bank.domain.EventStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositCommandHandlerTest {

    private final UUID ACCOUNT_ID = UUID.fromString("00000000-000-0000-0000-000000000001");

    @Mock
    private EventStore eventStore;

    @Test
    void handle_should_increase_and_save_account() {
        var depositCommandHandler = new DepositCommandHandler(eventStore);

        depositCommandHandler.handle(new DepositCommand(ACCOUNT_ID, 100));

        verify(eventStore).save(new Account(List.of(new AccountCreated(ACCOUNT_ID), new AmountDeposited(100))));
    }
}
