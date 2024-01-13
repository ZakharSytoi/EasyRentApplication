package com.example.deploydemo.service;

import com.example.deploydemo.repository.daos.ApartmentRepository;
import com.example.deploydemo.repository.model.Apartment;
import com.example.deploydemo.service.dto.ApartmentRequestDto;
import com.example.deploydemo.service.dto.ApartmentResponseDto;
import com.example.deploydemo.service.exception.ApartmentNotFoundException;
import com.example.deploydemo.service.mapper.ApartmentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final UserUtil userUtil;
    private final ApartmentMapper apartmentMapper;

    public Page<ApartmentResponseDto> getAllOwnersApartments(int number, int size) {
        return apartmentRepository.findAllByOwner_Id(userUtil.getUserIdFromContext(), PageRequest.of(number, size))
                .map(apartmentMapper::apartmentToApartmentDto);
    }

    public ApartmentResponseDto getApartmentById(Long id) {
        Long userId = userUtil.getUserIdFromContext();
        return apartmentMapper.apartmentToApartmentDto(apartmentRepository.findByIdAndOwner_id(id, userId).orElseThrow(
                () -> new ApartmentNotFoundException(
                        String.format("Apartment with id = %s not found or not belong to user with id = %s", id, userId)
                )
        ));
    }

    public URI createApartment(ApartmentRequestDto apartmentRequestDto) {
        Apartment apartment = apartmentMapper.apartmentFromDtoRequest(apartmentRequestDto);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/easyrent-api/v1/apartments/")
                .path(apartmentRepository.save(apartment).getId().toString())
                .build().toUri();
    }

    public void updateApartment(Long id, ApartmentRequestDto apartmentRequestDto) {
        Long userId = userUtil.getUserIdFromContext();
        Apartment apartment = apartmentRepository.findByIdAndOwner_id(id, userId).orElseThrow(
                () -> new ApartmentNotFoundException(
                        String.format("Apartment with id = %s not found or not belong to user with id = %s", id, userId)
                )
        );
        apartmentMapper.updateApartmentFromDto(apartmentRequestDto, apartment);
        apartmentRepository.save(apartment);
    }

    @Transactional
    public void deleteApartment(Long id) {
        apartmentRepository.deleteByIdAndOwner_Id(id, userUtil.getUserIdFromContext());
    }
}
