package ru.mail.cache.invoker;

import org.apache.commons.lang3.SerializationException;
import ru.mail.cache.Cache;
import ru.mail.cache.common.DefaultSettings;
import ru.mail.cache.common.Key;
import ru.mail.cache.common.ZipUtils;
import ru.mail.cache.exception.DeleteFileException;
import ru.mail.cache.exception.SaveFileException;

import java.io.*;
import java.lang.reflect.Method;

public class FileInvoker extends AbstractInvoke {

    private static final String FILE_NAME_PATTERN = "%s/%s%s-%s";

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, DefaultSettings defaultSettings) throws IOException {
        Cache cache = method.getAnnotation(Cache.class);
        String fileName = getFileName(method, args, defaultSettings);
        Object result;

        if (isCached(fileName, cache.zip())) {
            result = getFromFile(fileName, cache.zip());
        } else {
            result = cutList(super.invoke(proxy, method, args), defaultSettings.getListSize(method));
            saveFile(fileName, result);
            if (cache.zip()) {
                ZipUtils.zipFile(fileName);
                deleteFile(new File(fileName));
            }
        }

        return result;
    }

    private boolean isCached(String fileName, boolean zip) {
        if (zip) {
            fileName = ZipUtils.toZipFileName(fileName);
        }
        return new File(fileName).exists();
    }

    private Object getFromFile(String fileName, boolean zip) throws IOException {
        Object result;
        File file = zip ? ZipUtils.unzipFile(fileName) : new File(fileName);
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            result = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException(e);
        }

        if (zip) {
            deleteFile(file);
        }
        return result;
    }

    private void saveFile(String fileName, Object result) throws IOException {
        File file = new File(fileName);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(result);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new SaveFileException(e);
        }
    }

    private void deleteFile(File file) {
        if (!file.delete()) {
            throw new DeleteFileException();
        }
    }

    private String getFileName(Method method, Object[] args, DefaultSettings defaultSettings) {
        String prefix = defaultSettings.getFileNamePrefix(method);
        Key key = new Key(method, args);
        return String.format(FILE_NAME_PATTERN, defaultSettings.getFilePath(), prefix, method.getName(), key.hashCode());
    }
}
