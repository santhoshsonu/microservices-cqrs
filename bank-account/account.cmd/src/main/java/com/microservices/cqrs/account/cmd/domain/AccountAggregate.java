package com.microservices.cqrs.account.cmd.domain;

import com.microservices.cqrs.account.cmd.api.commands.OpenAccountCommand;
import com.microservices.cqrs.account.common.domain.AggregateRoot;
import com.microservices.cqrs.account.common.events.AccountClosedEvent;
import com.microservices.cqrs.account.common.events.AccountOpenedEvent;
import com.microservices.cqrs.account.common.events.FundsDepositedEvent;
import com.microservices.cqrs.account.common.events.FundsWithdrawnEvent;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
  private boolean active;
  private double balance;

  public AccountAggregate(OpenAccountCommand command) {
    raiseEvent(
        AccountOpenedEvent.builder()
            .id(command.getId())
            .accountHolder(command.getAccountHolder())
            .accountType(command.getAccountType())
            .openingBalance(command.getOpeningBalance())
            .createdDate(LocalDateTime.now())
            .build());
  }

  public void apply(AccountOpenedEvent event) {
    this.id = event.getId();
    this.active = true;
    this.balance = event.getOpeningBalance();
  }

  public void depositFunds(double amount) {
    if (!this.active) {
      throw new IllegalStateException("Funds cannot be deposited into a closed account!");
    }
    if (amount <= 0) {
      throw new IllegalStateException("Deposit amount must be greater than 0!");
    }
    raiseEvent(FundsDepositedEvent.builder().id(this.id).amount(amount).build());
  }

  public void apply(FundsDepositedEvent event) {
    this.id = event.getId();
    this.balance += event.getAmount();
  }

  public void withdrawFunds(double amount) {
    if (!this.active) {
      throw new IllegalStateException("Funds cannot be deposited into a closed account!");
    }
    if (amount <= 0) {
      throw new IllegalStateException("Withdraw amount must be greater than 0!");
    }
    if (this.balance < amount) {
      throw new IllegalStateException("Insufficient funds!");
    }
    raiseEvent(FundsWithdrawnEvent.builder().id(this.id).amount(amount).build());
  }

  public void apply(FundsWithdrawnEvent event) {
    this.id = event.getId();
    this.balance -= event.getAmount();
  }

  public void closeAccount() {
    if (!this.active) {
      throw new IllegalStateException("The bank account has already been closed!");
    }
    raiseEvent(AccountClosedEvent.builder().id(this.id).build());
  }

  public void apply(AccountClosedEvent event) {
    this.id = event.getId();
    this.active = false;
  }
}
