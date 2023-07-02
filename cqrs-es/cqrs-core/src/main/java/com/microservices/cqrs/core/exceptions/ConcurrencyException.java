package com.microservices.cqrs.core.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConcurrencyException extends RuntimeException {

  public ConcurrencyException(String message) {
    super(message);
  }
}
