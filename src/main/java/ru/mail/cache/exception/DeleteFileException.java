package ru.mail.cache.exception;

public class DeleteFileException extends RuntimeException {

    public DeleteFileException() {
        super("Unable to delete file");
    }

    public DeleteFileException(Exception e) {
        super("Unable to delete file", e);
    }
}
