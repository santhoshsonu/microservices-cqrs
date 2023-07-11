package com.microservices.cqrs.account.query.api.queries;

import com.microservices.cqrs.core.domain.BaseEntity;
import com.microservices.cqrs.core.exceptions.AccountNotFoundException;

import java.util.List;

public interface QueryHandler {
  List<BaseEntity> handle(FindAllAccountsQuery query);

  List<BaseEntity> handle(FindAccountByIdQuery query) throws AccountNotFoundException;

  List<BaseEntity> handle(FindAccountByHolderQuery query);

  List<BaseEntity> handle(FindAccountWithBalanceQuery query);
}
