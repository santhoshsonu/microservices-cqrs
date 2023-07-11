package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CloseAccountCommand extends BaseCommand {
  public CloseAccountCommand(String id) {
    super(id);
  }
}
