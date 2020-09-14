package dev.mflash.guides.logging.aspect

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase

class AspectAppender extends AppenderBase<ILoggingEvent> {

  def events = new ArrayList<ILoggingEvent>()

  @Override
  protected void append(ILoggingEvent event) {
    events.add(event)
  }
}