package ru.mail.cache.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public List<String> inFileZipPrefix(String item, Date date) {
        return doHardWork(item, date);
    }

    @Override
    public List<String> inFile(String item, Date date) {
        return doHardWork(item, date);
    }

    @Override
    public List<String> inMemoryListSizeIdentityBy(String item, Date date) {
        return doHardWork(item, date);
    }

    @Override
    public List<String> inMemory(String item, Date date) {
        return doHardWork(item, date);
    }

    private List<String> doHardWork(String item, Date date) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(new Date().toString());
        }
        return list;
    }
}

