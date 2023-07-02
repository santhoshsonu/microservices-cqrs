package com.microservices.cqrs.core.events;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collation = "eventStore")
public class EventModel {
  @Id private String id;
  private ZonedDateTime timestamp;
  private String aggregateIdentifier;
  private String aggregateType;
  private int version;
  private String eventType;
  private BaseEvent eventData;
}
