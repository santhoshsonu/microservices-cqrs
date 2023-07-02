package com.microservices.cqrs.account.query.infrastructure.handlers;

import static java.util.Objects.isNull;

import com.microservices.cqrs.account.common.events.AccountClosedEvent;
import com.microservices.cqrs.account.common.events.AccountOpenedEvent;
import com.microservices.cqrs.account.common.events.FundsDepositedEvent;
import com.microservices.cqrs.account.common.events.FundsWithdrawnEvent;
import com.microservices.cqrs.account.query.domain.AccountRepository;
import com.microservices.cqrs.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {
  private final AccountRepository accountRepository;

  @Autowired
  public AccountEventHandler(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void on(AccountOpenedEvent event) {
    accountRepository.save(
        new BankAccount()
            .setId(event.getId())
            .setAccountType(event.getAccountType())
            .setBalance(event.getOpeningBalance())
            .setCreatedAt(event.getCreatedDate()));
  }

  @Override
  public void on(FundsDepositedEvent event) {
    BankAccount account = accountRepository.findById(event.getId()).orElse(null);
    if (isNull(account)) {
      return;
    }
    account.setBalance(account.getBalance() + event.getAmount());
    accountRepository.save(account);
  }

  @Override
  public void on(FundsWithdrawnEvent event) {
    BankAccount account = accountRepository.findById(event.getId()).orElse(null);
    if (isNull(account)) {
      return;
    }
    account.setBalance(account.getBalance() - event.getAmount());
    accountRepository.save(account);
  }

  @Override
  public void on(AccountClosedEvent event) {
    accountRepository.deleteById(event.getId());
  }
}
