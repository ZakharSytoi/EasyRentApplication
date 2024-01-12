package com.example.deploydemo.service;

import com.example.deploydemo.repository.daos.ApartmentRepository;
import com.example.deploydemo.repository.daos.UserRepository;
import com.example.deploydemo.repository.model.Apartment;
import com.example.deploydemo.service.dto.ApartmentResponseDto;
import com.example.deploydemo.service.dto.ApartmentRequestDto;
import com.example.deploydemo.service.exception.ApartmentNotFoundException;
import com.example.deploydemo.service.mapper.ApartmentMapper;
import com.example.deploydemo.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final UserUtil userUtil;
    private final ApartmentMapper apartmentMapper = Mappers.getMapper(ApartmentMapper.class);
    private final UserRepository userRepository;

    public Page<ApartmentResponseDto> getAllOwnersApartments() {
        return apartmentRepository.findAllByUserId(userUtil.getUserIdFromContext())
                .map(apartmentMapper::apartmentToApartmentDto);
    }

    public ApartmentResponseDto getApartmentById(Long id) {
        return apartmentMapper.apartmentToApartmentDto(apartmentRepository.findByIdAndUserId(id, userUtil.getUserIdFromContext()).orElseThrow(
                () -> new ApartmentNotFoundException(String.format("Apartment with id = %s not found", id))
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
        if(apartmentRepository.existsById(id)){
            Apartment apartment = apartmentMapper.apartmentFromDtoRequest(apartmentRequestDto);
            apartment.setId(id);
            apartmentRepository.save(apartment);
        }
        else throw new ApartmentNotFoundException(String.format("Apartment with id = %s not found", id));
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteByIdAndUserId(id, userUtil.getUserIdFromContext());
    }
}
