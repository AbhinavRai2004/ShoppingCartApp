package com.example.shoppingcart.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String massage) {
        super(massage);
    }
}
