package com.microservices.cqrs.account.cmd.infrastructure;

import static java.util.Objects.isNull;

import com.microservices.cqrs.account.cmd.domain.AccountAggregate;
import com.microservices.cqrs.account.cmd.domain.EventStoreRepository;
import com.microservices.cqrs.core.events.BaseEvent;
import com.microservices.cqrs.core.events.EventModel;
import com.microservices.cqrs.core.exceptions.AggregateNotFoundException;
import com.microservices.cqrs.core.exceptions.ConcurrencyException;
import com.microservices.cqrs.core.infrastructure.EventStore;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventStore implements EventStore {
  private final EventStoreRepository eventStoreRepository;
  private final AccountEventProducer eventProducer;

  @Autowired
  public AccountEventStore(
      EventStoreRepository eventStoreRepository, AccountEventProducer eventProducer) {
    this.eventStoreRepository = eventStoreRepository;
    this.eventProducer = eventProducer;
  }

  @Override
  public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
    final List<EventModel> eventModels =
        eventStoreRepository.findByAggregateIdentifier(aggregateId);
    if (expectedVersion != -1
        && eventModels.get(eventModels.size() - 1).getVersion() != expectedVersion) {
      throw new ConcurrencyException("Expected and latest version did not match!");
    }
    int version = expectedVersion;
    for (var event : events) {
      version++;
      event.setVersion(version);
      EventModel model =
          new EventModel()
              .setTimestamp(ZonedDateTime.now(ZoneOffset.UTC))
              .setAggregateIdentifier(aggregateId)
              .setAggregateType(AccountAggregate.class.getTypeName())
              .setEventData(event)
              .setEventType(event.getClass().getTypeName())
              .setVersion(version);
      eventStoreRepository.save(model);
      eventProducer.produce(event.getClass().getSimpleName(), event);
    }
  }

  @Override
  public List<BaseEvent> getEvents(String aggregateId) {
    final List<EventModel> eventModels =
        eventStoreRepository.findByAggregateIdentifier(aggregateId);
    if (isNull(eventModels) || eventModels.isEmpty()) {
      throw new AggregateNotFoundException("Incorrect account ID provided!");
    }
    return eventModels.stream().map(EventModel::getEventData).toList();
  }
}
