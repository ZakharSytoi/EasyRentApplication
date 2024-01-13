package com.example.deploydemo.service.mapper;

import com.example.deploydemo.repository.model.RentContract;
import com.example.deploydemo.repository.model.Tenant;
import com.example.deploydemo.service.dto.TenantCreateRequestDto;
import com.example.deploydemo.service.dto.TenantResponseDto;
import com.example.deploydemo.service.dto.TenantUpdateRequestDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class TenantMapper {
    public abstract TenantResponseDto tenantToResponseDto(Tenant tenant);
    @Mapping(target = "rentContract", expression = "java( rentContract )")
    public abstract Tenant tenantFromRequestDto(TenantCreateRequestDto createRequestDto, @Context RentContract rentContract);
    public abstract void updateTenantFromRequestDto(Tenant tenant, @MappingTarget TenantUpdateRequestDto updateRequestDto);
}
