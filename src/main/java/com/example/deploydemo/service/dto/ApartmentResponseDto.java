package com.example.deploydemo.service.dto;

public record ApartmentResponseDto(
        Long apartment_id,
        String address,
        String note
) {
}
