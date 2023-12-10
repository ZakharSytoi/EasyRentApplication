package com.example.deploydemo;

public record ResponseApartmentObject(
        String name,
        Long ownerId,
        String address,
        String note
) {
}
