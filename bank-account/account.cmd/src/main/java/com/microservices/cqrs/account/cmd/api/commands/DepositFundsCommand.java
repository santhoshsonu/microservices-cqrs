package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.account.common.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DepositFundsCommand extends BaseCommand {
  private double amount;
}