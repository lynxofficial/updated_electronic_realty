package ru.realty.erealty.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.realty.erealty.constant.UserRole;
import ru.realty.erealty.support.BaseSpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;

public class CustomAuthSuccessHandlerTest extends BaseSpringBootTest {
    @Test
    void onAuthenticationSuccessShouldWorkWithAdminRole() {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = new TestingAuthenticationToken("test", "test",
                UserRole.ADMIN_ROLE);

        assertThatNoException()
                .isThrownBy(() -> customAuthSuccessHandler.onAuthenticationSuccess(
                        httpServletRequest,
                        httpServletResponse,
                        authentication
                ));
    }

    @Test
    void onAuthenticationSuccessShouldWorkWithUserRole() {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = new TestingAuthenticationToken("test", "test",
                UserRole.USER_ROLE);

        assertThatNoException()
                .isThrownBy(() -> customAuthSuccessHandler.onAuthenticationSuccess(
                        httpServletRequest,
                        httpServletResponse,
                        authentication
                ));
    }
}
