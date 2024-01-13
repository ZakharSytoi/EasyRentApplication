package com.example.deploydemo.service.dto;

public record TenantCreateRequestDto (
        String name,
        String surname,
        String email,
        String phoneNumber
){
}
