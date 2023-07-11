package com.microservices.cqrs.account.query.infrastructure.consumers;

import com.microservices.cqrs.account.common.events.AccountClosedEvent;
import com.microservices.cqrs.account.common.events.AccountOpenedEvent;
import com.microservices.cqrs.account.common.events.FundsDepositedEvent;
import com.microservices.cqrs.account.common.events.FundsWithdrawnEvent;
import com.microservices.cqrs.account.query.infrastructure.handlers.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountEventConsumer implements EventConsumer {

  private final EventHandler eventHandler;

  @Autowired
  public AccountEventConsumer(EventHandler eventHandler) {
    this.eventHandler = eventHandler;
  }

  @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(AccountOpenedEvent event, Acknowledgment ack) {
    log.info("Topic: AccountOpenedEvent received event: {}", event);
    eventHandler.on(event);
    ack.acknowledge();
  }

  @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(FundsDepositedEvent event, Acknowledgment ack) {
    log.info("Topic: FundsDepositedEvent received event: {}", event);
    eventHandler.on(event);
    ack.acknowledge();
  }

  @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(FundsWithdrawnEvent event, Acknowledgment ack) {
    log.info("Topic: FundsWithdrawnEvent received event: {}", event);
    eventHandler.on(event);
    ack.acknowledge();
  }

  @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(AccountClosedEvent event, Acknowledgment ack) {
    log.info("Topic: AccountClosedEvent received event: {}", event);
    eventHandler.on(event);
    ack.acknowledge();
  }
}
