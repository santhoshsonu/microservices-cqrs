package com.microservices.cqrs.account.common.events;

import com.microservices.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {}
