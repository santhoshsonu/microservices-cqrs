package com.microservices.cqrs.account.common.events;

import com.microservices.cqrs.account.common.dto.AccountType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
  private String accountHolder;
  private AccountType accountType;
  private LocalDateTime createdDate;
  private double openingBalance;
}
