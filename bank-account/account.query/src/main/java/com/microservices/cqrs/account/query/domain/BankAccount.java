package com.microservices.cqrs.account.query.domain;

import com.microservices.cqrs.account.common.dto.AccountType;
import com.microservices.cqrs.core.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity(name = "bankAccount")
public class BankAccount extends BaseEntity {
  @Id private String id;
  private String accountHolder;
  private AccountType accountType;
  private double balance;
  private ZonedDateTime createdAt;
}
