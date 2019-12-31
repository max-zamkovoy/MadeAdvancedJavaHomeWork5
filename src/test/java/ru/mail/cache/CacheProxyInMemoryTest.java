package ru.mail.cache;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import ru.mail.cache.common.DefaultSettings;
import ru.mail.cache.service.Service;
import ru.mail.cache.service.ServiceImpl;

import java.util.Calendar;
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
        assertSame(list, service.inMemory(TEXT, DATE));
    }

    @Test
    public void inMemoryNotCache() {
        Service service = cacheService(null);
        List<String> list = service.inMemory(TEXT, DATE);
        assertNotSame(list, service.inMemory(TEXT, getNextDate(1)));
    }

    @Test
    public void inMemoryWithIdentityBy() {
        Service service = cacheService(null);
        List<String> list = service.inMemoryListSizeIdentityBy(TEXT, DATE);
        assertEquals(list, service.inMemoryListSizeIdentityBy(TEXT, getNextDate(2)));
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
        assertEquals(5, serviceSizeList.inMemory(TEXT, getNextDate(3)).size());
    }

    private Service cacheService(DefaultSettings settings) {
        CacheProxy<Service> cacheProxy = new CacheProxy<>(StringUtils.EMPTY, settings);
        return cacheProxy.cache(new ServiceImpl());
    }

    private Date getNextDate(int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE);
        calendar.add(Calendar.DATE, number);
        return calendar.getTime();
    }
}
