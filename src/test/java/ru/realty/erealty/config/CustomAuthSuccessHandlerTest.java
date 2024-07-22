package ru.realty.erealty.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.realty.erealty.support.BaseSpringBootTest;

public class CustomAuthSuccessHandlerTest extends BaseSpringBootTest {
    @Test
    void onAuthenticationSuccessShouldWorkWithAdminRole() {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = new TestingAuthenticationToken("test", "test",
                "ROLE_ADMIN");

        Assertions.assertThatNoException()
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
                "ROLE_USER");

        Assertions.assertThatNoException()
                .isThrownBy(() -> customAuthSuccessHandler.onAuthenticationSuccess(
                        httpServletRequest,
                        httpServletResponse,
                        authentication
                ));
    }
}
