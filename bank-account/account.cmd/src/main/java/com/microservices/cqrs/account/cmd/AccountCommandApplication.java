package com.microservices.cqrs.account.cmd;

import com.microservices.cqrs.account.cmd.api.commands.*;
import com.microservices.cqrs.core.commands.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountCommandApplication {

  @Autowired private CommandHandler commandHandler;
  @Autowired private CommandDispatcher commandDispatcher;

  public static void main(String[] args) {
    SpringApplication.run(AccountCommandApplication.class);
  }

  @PostConstruct
  public void registerHandlers() {
    commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
    commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
    commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
    commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
  }
}
