package com.microservices.cqrs.account.cmd.api.commands;

import com.microservices.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class DepositFundsCommand extends BaseCommand {
  private double amount;
}
