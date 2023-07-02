package com.microservices.cqrs.core.producers;

import com.microservices.cqrs.core.events.BaseEvent;

public interface EventProducer {
  void produce(String topic, BaseEvent event);
}
