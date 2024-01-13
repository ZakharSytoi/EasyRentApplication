package com.example.deploydemo.service.dto;

public record TenantUpdateRequestDto (
        Long id,
        String name,
        String surname,
        String email,
        String phoneNumber
){
}
