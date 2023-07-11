package com.microservices.cqrs.account.query.api.queries;

import com.microservices.cqrs.account.query.api.dto.EqualityType;
import com.microservices.cqrs.account.query.domain.AccountRepository;
import com.microservices.cqrs.account.query.domain.BankAccount;
import com.microservices.cqrs.core.domain.BaseEntity;
import com.microservices.cqrs.core.exceptions.AccountNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryHandler implements QueryHandler {
  private final AccountRepository accountRepository;

  @Autowired
  public AccountQueryHandler(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public List<BaseEntity> handle(FindAllAccountsQuery query) {
    Iterable<BankAccount> accountIterable = accountRepository.findAll();
    final List<BaseEntity> bankAccounts = new ArrayList<>();
    accountIterable.forEach(bankAccounts::add);
    return bankAccounts;
  }

  @Override
  public List<BaseEntity> handle(FindAccountByIdQuery query) throws AccountNotFoundException {
    return List.of(
        accountRepository
            .findById(query.getId())
            .orElseThrow(
                () ->
                    new AccountNotFoundException(
                        MessageFormat.format(
                            "Provided account ID : {0} is incorrect!", query.getId()))));
  }

  @Override
  public List<BaseEntity> handle(FindAccountByHolderQuery query) {
    return new ArrayList<>(accountRepository.findByAccountHolder(query.getAccountHolder()));
  }

  @Override
  public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
    final List<BaseEntity> bankAccounts = new ArrayList<>();
    if (EqualityType.GREATER_THAN.equals(query.getEqualityType())) {
      bankAccounts.addAll(accountRepository.findByBalanceGreaterThan(query.getBalance()));
    } else if (EqualityType.LESS_THAN.equals(query.getEqualityType())) {
      bankAccounts.addAll(accountRepository.findByBalanceLessThan(query.getBalance()));
    }
    return bankAccounts;
  }
}
