package ru.practicum.shareit.item;

import org.springframework.http.HttpHeaders;


public class CustomHttpHeaders extends HttpHeaders {
    public static final String userId = "X-Sharer-User-Id";

    public CustomHttpHeaders() {

    }

}