package com.amiroffir.shoppingsystem.exceptions;

public class EmptyResultException extends RuntimeException{
    public EmptyResultException(String message) {
        super(message);
    }
    public EmptyResultException() {
        super();
    }
}