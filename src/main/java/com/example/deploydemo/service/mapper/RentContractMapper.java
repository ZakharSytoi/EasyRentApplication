package com.example.deploydemo.service.mapper;

import com.example.deploydemo.repository.model.Apartment;
import com.example.deploydemo.repository.model.RentContract;
import com.example.deploydemo.service.dto.RentContractCreateRequestDto;
import com.example.deploydemo.service.dto.RentContractResponseDto;
import com.example.deploydemo.service.dto.RentContractUpdateRequestDto;
import com.example.deploydemo.service.UserUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RentContractMapper {
    @Autowired
    protected UserUtil userUtil;
    public abstract RentContractResponseDto rentContractToResponseDto(RentContract rentContract);

    @Mappings({
            @Mapping(target = "apartment", expression = "java(apartment)"),
            @Mapping(target = "residentUser", expression = "java(userUtil.getUserById(rentContractDto.residentUserId()))"),
    })
    public abstract RentContract rentContractFromRequestDto(RentContractCreateRequestDto rentContractDto,
                                                            @Context Apartment apartment);

    public abstract void updateRentContractFromDto(RentContractUpdateRequestDto rentContractDto, @MappingTarget RentContract rentContract);
}
