package com.mesh.bankservice.controller.mapper;

import com.mesh.bankservice.model.PhoneData;
import com.mesh.bankservice.model.dto.PhoneDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneDataDtoMapper {
    PhoneDataDto fromPhoneData(PhoneData phoneData);
}
