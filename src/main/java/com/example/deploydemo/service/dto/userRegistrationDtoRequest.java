package com.example.deploydemo.service.dto;

public record userRegistrationDtoRequest(String name,
                                         String surname,
                                         String email,
                                         String password,
                                         String contact_email,
                                         String phone_number) {
}
