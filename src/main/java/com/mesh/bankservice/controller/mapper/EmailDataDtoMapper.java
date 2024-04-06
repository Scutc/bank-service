package com.mesh.bankservice.controller.mapper;

import java.util.List;

import com.mesh.bankservice.model.EmailData;
import com.mesh.bankservice.model.dto.EmailDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailDataDtoMapper {
    EmailDataDto fromEmailData(EmailData emailData);

    List<EmailDataDto> fromEmailsData(List<EmailData> emailsData);
}
