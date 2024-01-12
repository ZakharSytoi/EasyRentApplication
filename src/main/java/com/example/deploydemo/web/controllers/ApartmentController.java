package com.example.deploydemo.web.controllers;

import com.example.deploydemo.service.ApartmentService;
import com.example.deploydemo.service.dto.ApartmentRequestDto;
import com.example.deploydemo.service.dto.ApartmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/easyrent-api/v1/apartments/")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<Page<ApartmentResponseDto>> getAllOwnersApartments() {
        return ResponseEntity.ok().body(apartmentService.getAllOwnersApartments());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ApartmentResponseDto> getApartmentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(apartmentService.getApartmentById(id));
    }

    @PostMapping()
    public ResponseEntity<?> createApartment(@RequestBody ApartmentRequestDto apartmentRequestDto) {
        return ResponseEntity.created(apartmentService.createApartment(apartmentRequestDto)).build();
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> updateApartment(@PathVariable Long id,
                                             @RequestBody ApartmentRequestDto apartmentRequestDto) {
        apartmentService.updateApartment(id, apartmentRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> deleteApartment(@PathVariable Long id){
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }
}
