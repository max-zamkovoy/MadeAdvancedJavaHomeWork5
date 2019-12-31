package ru.mail.cache.service;

import ru.mail.cache.Cache;
import ru.mail.cache.CacheType;

import java.util.Date;
import java.util.List;

public interface Service {

    @Cache(cacheType = CacheType.IN_FILE, fileNamePrefix = "Cache-", zip = true)
    List<String> inFileZipPrefix(String item, Date date);

    @Cache(cacheType = CacheType.IN_FILE)
    List<String> inFile(String item, Date date);

    @Cache(cacheType = CacheType.IN_MEMORY, listSize = 10, identityBy = {String.class})
    List<String> inMemoryListSizeIdentityBy(String item, Date date);

    @Cache(cacheType = CacheType.IN_MEMORY)
    List<String> inMemory(String item, Date date);
}
