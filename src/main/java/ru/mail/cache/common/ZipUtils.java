package ru.mail.cache.common;

import org.apache.commons.lang3.SerializationException;
import ru.mail.cache.exception.ZipException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private static final String EXTENSION = ".zip";

    public static String toZipFileName(String fileName) {
        return fileName + EXTENSION;
    }

    public static void zipFile(String fileName) throws SerializationException {
        File source = new File(fileName);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(toZipFileName(fileName)));
             FileInputStream fileInputStream = new FileInputStream(source)) {

            ZipEntry entry = new ZipEntry(source.getName());
            zipOutputStream.putNextEntry(entry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fileInputStream.read(bytes)) >= 0) {
                zipOutputStream.write(bytes, 0, length);
            }
        } catch (IOException e) {
            throw new ZipException(fileName, e);
        }
    }

    public static File unzipFile(String fileName) throws IOException {
        File newFile = null;
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(toZipFileName(fileName)));
        ZipEntry zipEntry = zis.getNextEntry();
        if (zipEntry != null) {
            newFile = new File(fileName + zipEntry.getName());
            FileOutputStream fos = new FileOutputStream(newFile);
            int length;
            while ((length = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
        }
        zis.closeEntry();
        zis.close();

        return newFile;
    }
}
