package ru.practicum.shareit.exeptions;

public class ConflictExeption extends RuntimeException {
    public ConflictExeption(String message) {
        super(message);
    }
}