package ru.practicum.shareit.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exeptions.ConflictExeption;
import ru.practicum.shareit.exeptions.EmptySearchQueryException;
import ru.practicum.shareit.exeptions.NotFoundExeption;
import ru.practicum.shareit.exeptions.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final EmptySearchQueryException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final NotFoundExeption e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(final ConflictExeption e) {
        System.out.println("Conflict Exception caught: " + e.getMessage()); // Диагностика
        return new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.toString());
    }
}