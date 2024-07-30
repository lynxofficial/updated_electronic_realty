package ru.realty.erealty.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.realty.erealty.dto.UserResponse;
import ru.realty.erealty.entity.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RealtyObjectMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "passwordForDigitalSignature", source = "passwordForDigitalSignature")
    @Mapping(target = "digitalSignature", source = "digitalSignature")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "enable", source = "enable")
    @Mapping(target = "verificationCode", source = "verificationCode")
    @Mapping(target = "passwordResetToken", source = "passwordResetToken")
    @Mapping(target = "realtyObjects", source = "realtyObjects")
    UserResponse toUserResponse(User user);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "passwordForDigitalSignature", source = "passwordForDigitalSignature")
    @Mapping(target = "digitalSignature", source = "digitalSignature")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "enable", source = "enable")
    @Mapping(target = "verificationCode", source = "verificationCode")
    @Mapping(target = "passwordResetToken", source = "passwordResetToken")
    @Mapping(target = "realtyObjects", source = "realtyObjects")
    List<UserResponse> toUserResponseList(List<User> users);
}
