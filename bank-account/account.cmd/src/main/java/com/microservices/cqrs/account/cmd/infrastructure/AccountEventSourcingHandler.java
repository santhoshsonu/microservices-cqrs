package com.microservices.cqrs.account.cmd.infrastructure;

import static java.util.Objects.nonNull;

import com.microservices.cqrs.account.cmd.domain.AccountAggregate;
import com.microservices.cqrs.core.domain.AggregateRoot;
import com.microservices.cqrs.core.events.BaseEvent;
import com.microservices.cqrs.core.handlers.EventSourcingHandler;
import com.microservices.cqrs.core.infrastructure.EventStore;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

  private final EventStore eventStore;

  @Autowired
  public AccountEventSourcingHandler(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @Override
  public void save(AggregateRoot aggregate) {
    eventStore.saveEvents(
        aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
    aggregate.markChangesAsCommitted();
  }

  @Override
  public AccountAggregate getById(String id) {
    final AccountAggregate aggregate = new AccountAggregate();
    List<BaseEvent> events = eventStore.getEvents(id);
    if (nonNull(events) && !events.isEmpty()) {
      aggregate.replayEvents(events);
      Integer latestVersion =
          events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder()).orElseThrow();
      aggregate.setVersion(latestVersion);
    }
    return aggregate;
  }
}
