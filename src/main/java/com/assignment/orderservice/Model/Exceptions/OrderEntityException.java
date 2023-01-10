package com.assignment.orderservice.Model.Exceptions;

public class OrderEntityException extends Throwable {
    public OrderEntityException(Exception e) {
        super(e);
    }
}
