package com.assignment.orderservice.Model.Exceptions;

public class ImproperItemDataException extends RuntimeException{
    public ImproperItemDataException(String s) {
        super(s);
    }
}
