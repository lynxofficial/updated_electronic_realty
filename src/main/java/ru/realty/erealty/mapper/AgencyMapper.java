package ru.realty.erealty.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.dto.AgencyResponse;
import ru.realty.erealty.entity.Agency;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AgencyMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    AgencyResponse toAgencyResponse(Agency agency);

    List<AgencyResponse> toAgencyResponseList(List<Agency> agencies);
}
