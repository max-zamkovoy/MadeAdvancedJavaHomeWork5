package ru.mail.cache.invoker;

import ru.mail.cache.common.DefaultSettings;

import java.io.IOException;
import java.lang.reflect.Method;

public interface Invoker {

    Object invoke(Object proxy, Method method, Object[] args, DefaultSettings defaultSettings) throws IOException;
}
