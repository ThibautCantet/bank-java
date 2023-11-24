package com.bank.command;

import java.util.List;

import com.bank.domain.AmountDeposited;
import com.bank.domain.EventStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositCommandHandlerTest {

    @Mock
    private EventStore eventStore;

    @Test
    void handle_should_increase_and_save_account() {
        var depositCommandHandler = new DepositCommandHandler(eventStore);

        depositCommandHandler.handle(new DepositCommand(100));

        verify(eventStore).add(List.of(new AmountDeposited(100)));
    }
}
