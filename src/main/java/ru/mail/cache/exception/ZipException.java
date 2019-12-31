package ru.mail.cache.exception;

public class ZipException extends RuntimeException {

    public ZipException(String fileName, Exception e) {
        super(String.format("Unable to zip file with name '%s'", fileName), e);
    }
}
