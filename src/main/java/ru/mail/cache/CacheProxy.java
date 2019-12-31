package ru.mail.cache;

import ru.mail.cache.common.DefaultSettings;

import java.lang.reflect.Proxy;

import static java.lang.ClassLoader.getSystemClassLoader;

public class CacheProxy<T> {

    private final DefaultSettings defaultSettings;

    public CacheProxy(String filePath, DefaultSettings defaultSettings) {
        this.defaultSettings = defaultSettings != null ? defaultSettings : new DefaultSettings();
        this.defaultSettings.setFilePath(filePath);
    }

    public CacheProxy(String filePath) {
        this(filePath, new DefaultSettings());
    }

    @SuppressWarnings("unchecked")
    public <T> T cache(T proxyObject) {
        return (T) Proxy.newProxyInstance(getSystemClassLoader(),
                proxyObject.getClass().getInterfaces(),
                new InvocationHandlerImpl(proxyObject, defaultSettings));
    }
}