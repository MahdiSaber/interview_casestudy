package com.trivago.casestudy.exception.domain;

public class PriceNegativeException extends Exception {
    public PriceNegativeException(String message) {
        super(message);
    }
}
