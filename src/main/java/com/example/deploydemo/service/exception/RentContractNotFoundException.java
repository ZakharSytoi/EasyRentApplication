package com.example.deploydemo.service.exception;

public class RentContractNotFoundException extends RuntimeException{
    public RentContractNotFoundException(String message) {
        super(message);
    }
}
