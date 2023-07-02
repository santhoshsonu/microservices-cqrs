package com.microservices.cqrs.core.handlers;

import com.microservices.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
  void save(AggregateRoot aggregate);

  T getById(String id);
}
