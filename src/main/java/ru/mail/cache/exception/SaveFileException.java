package ru.mail.cache.exception;

public class SaveFileException extends RuntimeException {

    public SaveFileException(Exception e) {
        super("Unable to save file", e);
    }
}
