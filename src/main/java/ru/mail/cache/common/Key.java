package ru.mail.cache.common;

import ru.mail.cache.Cache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Key {

    private String methodName;
    private List<Object> params;

    public Key(Method method, Object... args) {
        methodName = method.getName();
        this.params = new ArrayList<>();
        Cache cache = method.getAnnotation(Cache.class);
        List<Class> classes = Arrays.asList(cache.identityBy());
        if (classes.isEmpty()) {
            params.addAll(Arrays.asList(args));
        } else {
            for (Object argument : args) {
                if (classes.contains(argument.getClass())) {
                    params.add(argument);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(methodName, key.methodName) &&
                Objects.equals(params, key.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, params);
    }
}
