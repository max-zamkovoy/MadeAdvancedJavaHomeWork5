package ru.mail.cache.common;

import org.apache.commons.lang3.StringUtils;
import ru.mail.cache.Cache;

import java.lang.reflect.Method;

public class DefaultSettings {

    private String fileNamePrefix = StringUtils.EMPTY;
    private int listSize = 100_000;
    private String filePath;


    public String getFileNamePrefix(Method method) {
        String prefix = method.getAnnotation(Cache.class).fileNamePrefix();
        return StringUtils.isBlank(prefix) ? fileNamePrefix : prefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public int getListSize(Method method) {
        int listSize = method.getAnnotation(Cache.class).listSize();
        return listSize < 0 ? this.listSize : listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
