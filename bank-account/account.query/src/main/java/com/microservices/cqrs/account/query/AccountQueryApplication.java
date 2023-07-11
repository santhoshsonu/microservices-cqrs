package com.microservices.cqrs.account.query;

import com.microservices.cqrs.account.query.api.queries.*;
import com.microservices.cqrs.account.query.infrastructure.AccountQueryDispatcher;
import com.microservices.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountQueryApplication {
  private final QueryDispatcher dispatcher;
  private final QueryHandler handler;

  @Autowired
  public AccountQueryApplication(AccountQueryDispatcher dispatcher, AccountQueryHandler handler) {
    this.dispatcher = dispatcher;
    this.handler = handler;
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountQueryApplication.class);
  }

  @PostConstruct
  public void register() {
    dispatcher.registerHandler(FindAllAccountsQuery.class, handler::handle);
    dispatcher.registerHandler(FindAccountByIdQuery.class, handler::handle);
    dispatcher.registerHandler(FindAccountByHolderQuery.class, handler::handle);
    dispatcher.registerHandler(FindAccountWithBalanceQuery.class, handler::handle);
  }
}
