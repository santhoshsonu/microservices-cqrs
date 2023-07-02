package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.core.commands.BaseCommand;
import com.microservices.cqrs.account.common.dto.AccountType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OpenAccountCommand extends BaseCommand {
  private String accountHolder;
  private AccountType accountType;
  private double openingBalance;
}
