package com.microservices.cqrs.account.query.infrastructure.handlers;

import com.microservices.cqrs.account.common.events.AccountClosedEvent;
import com.microservices.cqrs.account.common.events.AccountOpenedEvent;
import com.microservices.cqrs.account.common.events.FundsDepositedEvent;
import com.microservices.cqrs.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
  void on(AccountOpenedEvent event);

  void on(FundsDepositedEvent event);

  void on(FundsWithdrawnEvent event);

  void on(AccountClosedEvent event);
}
