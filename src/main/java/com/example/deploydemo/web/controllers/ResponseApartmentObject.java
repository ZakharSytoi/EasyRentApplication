package com.example.deploydemo.web.controllers;

public record ResponseApartmentObject(
        String name,
        Long ownerId,
        String address,
        String note
) {
}
