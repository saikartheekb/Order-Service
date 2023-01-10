package com.assignment.orderservice.Model.Exceptions;

public class CustomerEntityException extends Exception {
    public CustomerEntityException(Exception e){
        super(e);
    }
}
