package com.bank.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountTest {

    @Test
    void deposit_should_increase_balance() {
        Account account = new Account();
        account.deposit(100);

        assertThat(account.getCurrentBalance()).isEqualTo(50);
        assertThat(account.getEvents()).containsExactly(new AmountDeposited(100));
    }

    @Test
    void deposit_should_not_change_balance_when_value_is_negative() {
        Account account = new Account();

        account.deposit(-50);

        assertThat(account.getCurrentBalance()).isEqualTo(100);
        assertThat(account.getEvents()).contains(new DepositRejected("Negative amount"));
    }

    @Test
    void withdraw_should_decrease_balance() {
        Account account = new Account(List.of(new AmountDeposited(100)));
        account.withdraw(50);

        assertThat(account.getCurrentBalance()).isEqualTo(50);
        assertThat(account.getEvents()).containsExactly(new AmountDeposited(100), new AmountWithDrawn(50));
    }

    @Test
    void withdraw_should_not_change_balance_when_insufficient_fund() {
        Account account = new Account();
        account.deposit(100);

        assertThat(account.getCurrentBalance()).isEqualTo(100);
        assertThat(account.getEvents()).contains(new WithdrawRejected("Insufficient funds"));
    }

    @Test
    void instantiation_should_initialize_the_balance_depending_of_the_previous_operations() {
        Account account = new Account(List.of(new AmountDeposited(100), new AmountWithDrawn(10)));

        assertThat(account.getCurrentBalance()).isEqualTo(90);
        assertThat(account.getEvents()).containsExactly(new AmountDeposited(100), new AmountWithDrawn(10));
    }
}

