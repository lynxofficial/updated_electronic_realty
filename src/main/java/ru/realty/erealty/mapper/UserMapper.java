package ru.realty.erealty.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.dto.UserResponse;
import ru.realty.erealty.entity.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RealtyObjectMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);
}
