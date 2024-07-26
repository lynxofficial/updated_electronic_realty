package ru.realty.erealty.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.dto.AgencyResponse;
import ru.realty.erealty.entity.Agency;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AgencyMapper {

    AgencyResponse toAgencyResponse(Agency agency);

    List<AgencyResponse> toAgencyResponseList(List<Agency> agencies);
}
