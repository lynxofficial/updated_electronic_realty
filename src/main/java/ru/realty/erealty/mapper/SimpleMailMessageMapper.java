package ru.realty.erealty.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.mail.SimpleMailMessage;
import ru.realty.erealty.dto.SimpleMailMessageDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SimpleMailMessageMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "from", source = "from")
    @Mapping(target = "to", source = "to")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "text", source = "text")
    SimpleMailMessage fromSimpleMailMessageDto(SimpleMailMessageDto simpleMailMessageDto);
}
