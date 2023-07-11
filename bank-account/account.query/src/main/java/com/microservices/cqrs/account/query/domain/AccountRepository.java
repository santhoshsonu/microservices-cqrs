package com.microservices.cqrs.account.query.domain;

import com.microservices.cqrs.core.domain.BaseEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {
  List<BankAccount> findByAccountHolder(String accountHolder);

  List<BaseEntity> findByBalanceGreaterThan(double balance);

  List<BaseEntity> findByBalanceLessThan(double balance);
}
