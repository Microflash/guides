package dev.mflash.guides.spring.aop.logging;

import dev.mflash.guides.spring.aop.logging.aspect.LogEntryExitAspect;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;

public final class ServiceProxyProvider {

  private ServiceProxyProvider() {}

  public static Object getServiceProxy(Object service) {
    final var entryExitAspect = new LogEntryExitAspect();
    final var proxyFactory = new AspectJProxyFactory(service);
    proxyFactory.addAspect(entryExitAspect);

    final AopProxy aopProxy = new DefaultAopProxyFactory().createAopProxy(proxyFactory);

    return aopProxy.getProxy();
  }
}
