package ru.realty.erealty.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.dto.RealtyObjectResponse;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RealtyObjectMapper {

    RealtyObjectResponse toRealtyObjectResponse(RealtyObject realtyObject);

    List<RealtyObjectResponse> toRealtyObjectResponseList(List<RealtyObject> realtyObjects);
}
