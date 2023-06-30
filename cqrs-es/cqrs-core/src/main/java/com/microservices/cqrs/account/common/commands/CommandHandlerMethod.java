package com.microservices.cqrs.account.common.commands;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
  void handle(T command);
}
