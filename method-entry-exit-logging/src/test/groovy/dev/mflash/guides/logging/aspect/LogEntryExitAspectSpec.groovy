package dev.mflash.guides.logging.aspect

import ch.qos.logback.classic.Logger
import dev.mflash.guides.logging.service.GreetingService
import org.slf4j.LoggerFactory
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.aop.framework.DefaultAopProxyFactory
import spock.lang.Shared
import spock.lang.Specification

class LogEntryExitAspectSpec extends Specification {

  @Shared GreetingService serviceProxy
  @Shared AspectAppender appender

  def setup() {
    def entryExitAspect = new LogEntryExitAspect()
    def aspectJProxyFactory = new AspectJProxyFactory(new GreetingService())
    aspectJProxyFactory.addAspect(entryExitAspect)

    def aopProxy = new DefaultAopProxyFactory().createAopProxy(aspectJProxyFactory)

    serviceProxy = aopProxy.getProxy() as GreetingService
    appender = new AspectAppender()
    appender.start()

    def logger = LoggerFactory.getLogger(GreetingService) as Logger
    logger.addAppender(appender)
  }

  def cleanup() {
    appender.stop()
  }

  def 'advice should fire with logs'() {
    when:
    serviceProxy.greet('Veronica')

    then:
    !appender.events.isEmpty()
    appender.events.any {
      event -> event.message == 'Started greet method with args: {name=Veronica}'
    }
    appender.events.any {
      event ->
        event.message.startsWith('Finished greet method in ') &&
            event.message.endsWith('millis with return: Hello, Veronica!')
    }
  }

  def 'advice should not fire on methods without annotation'() {
    when:
    serviceProxy.resolveName('Veronica')

    then:
    appender.events.isEmpty()
  }
}
