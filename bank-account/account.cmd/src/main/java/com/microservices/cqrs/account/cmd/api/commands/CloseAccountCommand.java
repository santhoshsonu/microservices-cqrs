package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
  public CloseAccountCommand(String id) {
    super(id);
  }
}
