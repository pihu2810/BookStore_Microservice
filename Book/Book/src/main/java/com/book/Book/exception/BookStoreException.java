package com.book.Book.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


public @Data
class BookStoreException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public BookStoreException(String message)
    {

        super(message);
    }

    public BookStoreException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }
}