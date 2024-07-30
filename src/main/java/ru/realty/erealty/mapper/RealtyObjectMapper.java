package ru.realty.erealty.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.dto.RealtyObjectResponse;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RealtyObjectMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "square", source = "square")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "user", source = "user")
    RealtyObjectResponse toRealtyObjectResponse(RealtyObject realtyObject);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "square", source = "square")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "user", source = "user")
    List<RealtyObjectResponse> toRealtyObjectResponseList(List<RealtyObject> realtyObjects);
}
