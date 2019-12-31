package ru.mail.cache;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import ru.mail.cache.common.DefaultSettings;
import ru.mail.cache.service.Service;
import ru.mail.cache.service.ServiceImpl;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CacheProxyInMemoryTest {

    private static final String TEXT = "TEXT";
    private static final Date DATE = new Date();

    @Test
    public void inMemoryCache() {
        Service service = cacheService(null);
        List<String> list = service.inMemory(TEXT, DATE);
        assertEquals(list, service.inMemory(TEXT, DATE));
    }

    @Test
    public void inMemoryNotCache() {
        Service service = cacheService(null);
        List<String> list = service.inMemory(TEXT, new Date());
        assertNotSame(list, service.inMemory(TEXT, new Date()));
    }

    @Test
    public void inMemoryWithIdentityBy() {
        Service service = cacheService(null);
        List<String> list = service.inMemoryListSizeIdentityBy(TEXT, new Date());
        assertEquals(list, service.inMemoryListSizeIdentityBy(TEXT, new Date()));
    }

    @Test
    public void inMemoryWithFullSizeList() {
        Service serviceSizeList = cacheService(null);
        assertEquals(50, serviceSizeList.inMemory(TEXT, DATE).size());
    }

    @Test
    public void inMemoryWithSizeList() {
        Service serviceSizeList = cacheService(null);
        assertEquals(10, serviceSizeList.inMemoryListSizeIdentityBy(TEXT, DATE).size());
    }

    @Test
    public void inMemoryWithSizeListDefaultSettings() {
        DefaultSettings settings = new DefaultSettings();
        settings.setListSize(5);
        Service serviceSizeList = cacheService(settings);
        assertEquals(5, serviceSizeList.inMemory(TEXT, DATE).size());
    }

    private Service cacheService(DefaultSettings settings) {
        CacheProxy<Service> cacheProxy = new CacheProxy<>(StringUtils.EMPTY, settings);
        return cacheProxy.cache(new ServiceImpl());
    }
}
