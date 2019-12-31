package ru.mail.cache;

import ru.mail.cache.invoker.FileInvoker;
import ru.mail.cache.invoker.Invoker;
import ru.mail.cache.invoker.MemoryInvoker;

public enum CacheType {

    IN_MEMORY(new MemoryInvoker()),
    IN_FILE(new FileInvoker());

    private Invoker invoker;

    CacheType(Invoker invoker) {
        this.invoker = invoker;
    }

    public Invoker getInvoker() {
        return invoker;
    }
}