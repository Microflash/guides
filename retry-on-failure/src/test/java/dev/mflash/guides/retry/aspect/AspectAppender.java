package dev.mflash.guides.retry.aspect;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class AspectAppender extends AppenderBase<ILoggingEvent> {

  List<ILoggingEvent> events = new ArrayList<>();

  protected @Override void append(ILoggingEvent event) {
    events.add(event);
  }
}
