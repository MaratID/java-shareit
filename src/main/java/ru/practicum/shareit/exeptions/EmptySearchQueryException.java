package ru.practicum.shareit.exeptions;

public class EmptySearchQueryException extends RuntimeException {
    public EmptySearchQueryException(String message) {
        super(message);
    }
}