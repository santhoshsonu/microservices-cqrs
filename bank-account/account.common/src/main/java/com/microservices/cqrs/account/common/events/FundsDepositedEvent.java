package com.microservices.cqrs.account.common.events;

import com.microservices.cqrs.account.common.dto.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class FundsDepositedEvent extends BaseEvent {
  private double amount;
}
