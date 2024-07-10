package ru.realty.erealty.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(new User(), new User()));
        List<User> users = userServiceImpl.findAll();
        Assertions.assertEquals(userRepository.findAll(), users);
    }

    @Test
    void findByEmailShouldWork() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));
        Assertions.assertNotNull(userServiceImpl.findByEmail(email));
    }

    @Test
    void deleteByIdDoesNotThrowException() {
        User user = new User();
        Mockito.doNothing()
                .when(userRepository)
                .deleteById(user.getUserId());
        Assertions.assertDoesNotThrow(() -> userServiceImpl.deleteById(user.getUserId()));
    }

    @Test
    void saveUserShouldWork() {
        User user = new User();
        user.setPassword("12345");
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        String url = "test.com";
        userServiceImpl.saveUser(user, url);
        Assertions.assertFalse(user.getEnable());
    }

    @Test
    void saveUserWithHttpSessionAndHttpServletRequestShouldWork() {
        User user = new User();
        user.setPassword("12345");
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        HttpSession httpSession = new MockHttpSession();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();
        userServiceImpl.saveUser(user, httpSession, httpServletRequest);
        Assertions.assertNotNull(httpSession.getAttribute("msg"));
    }

    @Test
    void removeSessionMessageDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> userServiceImpl.removeSessionMessage());
    }

    @Test
    void hasExpiredShouldWork() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Assertions.assertFalse(userServiceImpl.hasExpired(localDateTime));
    }

    @Test
    void verifyAccountShouldWork() {
        String verificationCode = "12345";
        User user = new User();
        user.setVerificationCode(verificationCode);
        Mockito.when(userRepository.findByVerificationCode(verificationCode))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        userServiceImpl.verifyAccount(verificationCode);
        Assertions.assertTrue(user.getEnable());
    }

    @Test
    void resetPasswordProcessDoesNotThrowException() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));
        user.setPassword("12345");
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Assertions.assertDoesNotThrow(() -> userServiceImpl.resetPasswordProcess(user));
    }

    @Test
    void isNotPositiveBalanceOrExistsDigitalSignatureShouldWork() {
        User currentUser = new User();
        currentUser.setBalance(BigDecimal.ZERO);
        User targetUser = new User();
        targetUser.setBalance(BigDecimal.valueOf(-1L));
        RealtyObject realtyObject = new RealtyObject();
        realtyObject.setPrice(BigDecimal.valueOf(100_000L));
        Assertions.assertTrue(userServiceImpl.isNotPositiveBalanceOrExistsDigitalSignature(currentUser,
                targetUser, realtyObject));
    }
}
