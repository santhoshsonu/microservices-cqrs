package com.microservices.cqrs.core.commands;

import com.microservices.cqrs.core.messages.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseCommand extends Message {
  public BaseCommand(String id) {
    super(id);
  }
}
