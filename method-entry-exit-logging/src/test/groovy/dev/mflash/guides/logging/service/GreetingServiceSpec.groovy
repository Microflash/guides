package dev.mflash.guides.logging.service

import dev.mflash.guides.logging.aspect.LogEntryExitAspect
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.aop.framework.DefaultAopProxyFactory
import spock.lang.Shared
import spock.lang.Specification

class GreetingServiceSpec extends Specification {
  @Shared GreetingService serviceProxy

  def setup() {
    def entryExitAspect = new LogEntryExitAspect()
    def aspectJProxyFactory = new AspectJProxyFactory(new GreetingService())
    aspectJProxyFactory.addAspect(entryExitAspect)

    def aopProxy = new DefaultAopProxyFactory().createAopProxy(aspectJProxyFactory)

    serviceProxy = aopProxy.getProxy() as GreetingService
  }

  def 'greeting should be returned'() {
    expect:
    serviceProxy.greet(name) == greeting

    where:
    name       | greeting
    'Veronica' | 'Hello, Veronica!'
    ''         | 'Hello, world!'
  }
}
