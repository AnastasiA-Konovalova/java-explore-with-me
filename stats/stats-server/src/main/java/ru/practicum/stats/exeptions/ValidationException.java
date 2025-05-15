package ru.practicum.stats.exeptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}