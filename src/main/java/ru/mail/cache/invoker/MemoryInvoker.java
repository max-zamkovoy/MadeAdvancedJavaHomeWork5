package ru.mail.cache.invoker;

import ru.mail.cache.common.DefaultSettings;
import ru.mail.cache.common.Key;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MemoryInvoker extends AbstractInvoke {

    private final Map<Key, Object> MEMORY_CACHE;

    public MemoryInvoker() {
        MEMORY_CACHE = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, DefaultSettings defaultSettings) {
        Key key = new Key(method, args);
        Object result = MEMORY_CACHE.get(key);
        if (result == null) {
            result = cutList(super.invoke(proxy, method, args), defaultSettings.getListSize(method));
            MEMORY_CACHE.put(key, result);
        }
        return result;
    }
}
