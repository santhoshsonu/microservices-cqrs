package com.microservices.cqrs.account.query.infrastructure;

import static java.util.Objects.isNull;

import com.microservices.cqrs.core.domain.BaseEntity;
import com.microservices.cqrs.core.infrastructure.QueryDispatcher;
import com.microservices.cqrs.core.queries.BaseQuery;
import com.microservices.cqrs.core.queries.QueryHandlerMethod;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

  private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

  @Override
  public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
    List<QueryHandlerMethod> handlers = routes.computeIfAbsent(type, e -> new LinkedList<>());
    handlers.add(handler);
  }

  @Override
  public <U extends BaseEntity> List<U> send(BaseQuery query) {
    List<QueryHandlerMethod> handlers = routes.get(query.getClass());
    if (isNull(handlers) || handlers.isEmpty()) {
      throw new RuntimeException("No query handler registered!");
    }
    if (handlers.size() > 1) {
      throw new RuntimeException("Cannot send query to more than one handler!");
    }
    return handlers.get(0).handle(query);
  }
}
