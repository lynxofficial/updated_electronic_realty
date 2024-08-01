package ru.realty.erealty.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.entity.support.MimeMessageHelperSupport;
import ru.realty.erealty.util.MimeMessageHelperMapperUtil;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = MimeMessageHelperMapperUtil.class)
public interface MimeMessageHelperMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "mimeMessageHelper",
            qualifiedByName = {"MimeMessageHelperMapperUtil", "generateMimeMessageHelper"},
            source = ".")
    @Mapping(target = "mimeMessage", source = "mimeMessage")
    @Mapping(target = "from", source = "from")
    @Mapping(target = "to", source = "to")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "content", source = "content")
    MimeMessageHelperSupport fromMimeMessageHelperDto(MimeMessageHelperDto mimeMessageHelperDto);
}
