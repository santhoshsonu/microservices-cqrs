package com.microservices.cqrs.account.common.commands;

public interface CommandDispatcher {
  <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

  void send(BaseCommand command);
}
