package com.microservices.cqrs.account.cmd.infrastructure;

import static java.util.Objects.isNull;

import com.microservices.cqrs.core.commands.BaseCommand;
import com.microservices.cqrs.core.commands.CommandDispatcher;
import com.microservices.cqrs.core.commands.CommandHandlerMethod;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
  private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes =
      new HashMap<>();

  @Override
  public <T extends BaseCommand> void registerHandler(
      Class<T> type, CommandHandlerMethod<T> handler) {
    var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
    handlers.add(handler);
  }

  @Override
  public void send(BaseCommand command) {
    var handlers = routes.get(command.getClass());
    if (isNull(handlers) || handlers.isEmpty()) {
      throw new RuntimeException("No command handler was registered!!");
    }
    if (handlers.size() > 1) {
      throw new RuntimeException("Cannot send command to more than one handler!");
    }
    handlers.get(0).handle(command);
  }
}
