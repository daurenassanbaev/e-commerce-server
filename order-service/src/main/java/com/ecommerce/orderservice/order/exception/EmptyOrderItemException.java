package com.ecommerce.orderservice.order.exception;

public class EmptyOrderItemException extends RuntimeException {

    public EmptyOrderItemException(String message) {
        super(message);
    }
}
