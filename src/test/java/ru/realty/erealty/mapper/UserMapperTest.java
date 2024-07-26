package ru.realty.erealty.mapper;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.dto.UserResponse;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    public void toUserResponseShouldWork() {
        User user = DataProvider.userBuilder().build();
        UserResponse actualUserResponse = DataProvider.userResponseBuilder().build();

        UserResponse expectedUserResponse = userMapper.toUserResponse(user);

        assertThat(actualUserResponse)
                .isEqualTo(expectedUserResponse);
    }

    @Test
    public void toUserResponseShouldNotWork() {
        User user = DataProvider.userBuilder().build();
        UserResponse actualUserResponse = DataProvider.userResponseBuilder()
                .id(123)
                .build();

        UserResponse expectedUserResponse = userMapper.toUserResponse(user);

        assertThat(actualUserResponse)
                .isNotEqualTo(expectedUserResponse);
    }

    @Test
    public void toUserResponseListShouldWork() {
        List<User> users = List.of(
                DataProvider.userBuilder().build(),
                DataProvider.userBuilder().build()
        );
        List<UserResponse> actualUserResponses = List.of(
                DataProvider.userResponseBuilder().build(),
                DataProvider.userResponseBuilder().build()
        );

        List<UserResponse> expectedUserResponses = userMapper.toUserResponseList(users);

        assertThat(actualUserResponses)
                .isEqualTo(expectedUserResponses);
    }

    @Test
    public void toUserResponseListShouldNotWork() {
        List<User> users = List.of(
                DataProvider.userBuilder().build(),
                DataProvider.userBuilder().build()
        );
        List<UserResponse> actualUserResponses = List.of(
                DataProvider.userResponseBuilder().build(),
                DataProvider.userResponseBuilder().build(),
                DataProvider.userResponseBuilder().build()
        );

        List<UserResponse> expectedUserResponses = userMapper.toUserResponseList(users);

        assertThat(actualUserResponses)
                .isNotEqualTo(expectedUserResponses);
    }
}
