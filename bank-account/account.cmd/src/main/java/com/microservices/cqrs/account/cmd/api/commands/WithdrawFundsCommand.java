package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WithdrawFundsCommand extends BaseCommand {
  private double amount;
}
