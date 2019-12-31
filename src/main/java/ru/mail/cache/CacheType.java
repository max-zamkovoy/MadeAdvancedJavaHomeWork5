package ru.mail.cache;

import ru.mail.cache.invoker.FileInvoker;
import ru.mail.cache.invoker.Invoker;
import ru.mail.cache.invoker.MemoryInvoker;

public enum CacheType {

    IN_MEMORY() {
        @Override
        public Invoker getInvoker() {
            return new MemoryInvoker();
        }
    },
    IN_FILE() {
        @Override
        public Invoker getInvoker() {
            return new FileInvoker();
        }
    };

    public abstract Invoker getInvoker();
}