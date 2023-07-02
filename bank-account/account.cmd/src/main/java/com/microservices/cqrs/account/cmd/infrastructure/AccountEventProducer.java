package com.microservices.cqrs.account.cmd.infrastructure;

import com.microservices.cqrs.core.events.BaseEvent;
import com.microservices.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  public AccountEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void produce(String topic, BaseEvent event) {
    kafkaTemplate.send(topic, event);
  }
}
