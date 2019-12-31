package ru.mail.cache.invoker;

import ru.mail.cache.common.DefaultSettings;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AbstractInvoke implements Invoker {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, DefaultSettings defaultSettings) throws IOException {
        return invoke(proxy, method, args);
    }

    protected Object invoke(Object proxy, Method method, Object[] args) {
        try {
            return method.invoke(proxy, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected Object cutList(Object result, int listSize) {
        if (result instanceof List) {
            List<?> list = (List)result;
            if (listSize < list.size()) {
                result = new ArrayList<>(list.subList(0, listSize));
            }
        }
        return result;
    }
}
