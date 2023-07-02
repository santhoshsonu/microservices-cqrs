package com.microservices.cqrs.core.domain;

import com.microservices.cqrs.core.events.BaseEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class AggregateRoot {
  @Setter(AccessLevel.NONE)
  protected String id;

  private int version = -1;

  @Getter(AccessLevel.NONE)
  private final List<BaseEvent> changes = new ArrayList<>();

  public List<BaseEvent> getUncommittedChanges() {
    return this.changes;
  }

  public void markChangesAsCommitted() {
    this.changes.clear();
  }

  protected void applyChange(BaseEvent event, boolean isNew) {
    try {
      var method = getClass().getMethod("apply", event.getClass());
      method.setAccessible(true);
      method.invoke(this, event);
    } catch (NoSuchMethodException e) {
      log.warn(
          MessageFormat.format(
              "The apply method was not found in the aggregate for {0}",
              event.getClass().getName()));
    } catch (Exception e) {
      log.error("Error applying event to aggregate", e);
    } finally {
      if (isNew) {
        changes.add(event);
      }
    }
  }

  public void raiseEvent(BaseEvent event) {
    applyChange(event, true);
  }

  public void replayEvents(Iterable<BaseEvent> events) {
    events.forEach(e -> applyChange(e, false));
  }
}
