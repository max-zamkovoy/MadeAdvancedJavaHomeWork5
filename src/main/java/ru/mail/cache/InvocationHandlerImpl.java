package ru.mail.cache;

import ru.mail.cache.common.DefaultSettings;
import ru.mail.cache.invoker.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class InvocationHandlerImpl implements InvocationHandler {

    private final Object proxyObject;
    private final DefaultSettings defaultSettings;

    public InvocationHandlerImpl(Object proxyObject, DefaultSettings defaultSettings) {
        this.proxyObject = proxyObject;
        this.defaultSettings = defaultSettings;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invoker invoker = method.getAnnotation(Cache.class).cacheType().getInvoker();
        return invoker.invoke(proxyObject, method, args, defaultSettings);
    }
}
