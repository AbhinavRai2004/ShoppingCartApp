package com.example.shoppingcart.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String massage) {
        super(massage);
    }
}
