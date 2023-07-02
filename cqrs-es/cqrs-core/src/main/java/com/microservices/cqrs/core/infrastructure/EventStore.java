package com.microservices.cqrs.core.infrastructure;

import com.microservices.cqrs.core.events.BaseEvent;
import java.util.List;

public interface EventStore {
  void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);

  List<BaseEvent> getEvents(String aggregateId);
}
