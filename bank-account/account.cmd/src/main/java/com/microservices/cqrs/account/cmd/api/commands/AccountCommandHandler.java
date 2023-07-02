package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.account.cmd.domain.AccountAggregate;
import com.microservices.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {
  private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

  @Autowired
  public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandler) {
    this.eventSourcingHandler = eventSourcingHandler;
  }

  @Override
  public void handle(OpenAccountCommand command) {
    final AccountAggregate aggregate = new AccountAggregate(command);
    eventSourcingHandler.save(aggregate);
  }

  @Override
  public void handle(DepositFundsCommand command) {
    final AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
    aggregate.depositFunds(command.getAmount());
    eventSourcingHandler.save(aggregate);
  }

  @Override
  public void handle(WithdrawFundsCommand command) {
    final AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
    aggregate.withdrawFunds(command.getAmount());
    eventSourcingHandler.save(aggregate);
  }

  @Override
  public void handle(CloseAccountCommand command) {
    final AccountAggregate aggregate = eventSourcingHandler.getById(command.getId());
    aggregate.closeAccount();
    eventSourcingHandler.save(aggregate);
  }
}
