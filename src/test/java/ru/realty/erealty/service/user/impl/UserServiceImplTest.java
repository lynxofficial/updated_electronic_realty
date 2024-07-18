package ru.realty.erealty.service.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        List<User> users = userServiceImpl.findAll();

        Assertions.assertEquals(userRepository.findAll(), users);
    }

    @Test
    void findAllShouldThrowsException() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        List<User> users = userServiceImpl.findAll();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> users.add(new User()));
    }

    @Test
    void findByEmailShouldWork() {
        String email = "test@test.com";
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        Assertions.assertNotNull(userServiceImpl.findByEmail(email));
    }

    @Test
    void findByEmailThrowsException() {
        String email = "test@test.com";
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.findByEmail(null));
    }

    @Test
    void deleteByIdDoesNotThrowException() {
        User user = DataProvider.userBuilder().build();
        Mockito.doNothing()
                .when(userRepository)
                .deleteById(user.getId());

        Assertions.assertDoesNotThrow(() -> userServiceImpl.deleteById(user.getId()));
    }

    @Test
    void saveUserShouldWork() {
        User user = DataProvider.userBuilder()
                .password("12345")
                .build();
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        String url = "test.com";

        userServiceImpl.saveUser(user, url);

        Assertions.assertFalse(user.getEnable());
    }

    @Test
    void saveUserThrowsException() {
        User user = DataProvider.userBuilder()
                .password("1234567")
                .build();
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        String url = "test.com";

        Assertions.assertThrows(IllegalArgumentException.class, () -> userServiceImpl.saveUser(new User(), url));
    }

    @Test
    void saveUserWithHttpSessionAndHttpServletRequestShouldWork() {
        User user = DataProvider.userBuilder()
                .password("12345")
                .build();
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        HttpSession httpSession = new MockHttpSession();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();

        userServiceImpl.saveUser(user, httpSession, httpServletRequest);

        Assertions.assertNotNull(httpSession.getAttribute("msg"));
    }

    @Test
    void saveUserWithHttpSessionAndHttpServletRequestThrowsException() {
        User user = DataProvider.userBuilder()
                .password(null)
                .build();
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        HttpSession httpSession = new MockHttpSession();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userServiceImpl.saveUser(user, httpSession, httpServletRequest));
    }

    @Test
    void removeSessionMessageThrowsExceptionException() {
        Assertions.assertThrows(NullPointerException.class, () -> userServiceImpl.removeSessionMessage());
    }

    @Test
    void hasExpiredShouldWork() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Assertions.assertFalse(userServiceImpl.hasExpired(localDateTime));
    }

    @Test
    void hasExpiredShouldNotWork() {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(45);

        Assertions.assertTrue(userServiceImpl.hasExpired(localDateTime));
    }

    @Test
    void verifyAccountShouldWork() {
        String verificationCode = "12345";
        User user = DataProvider.userBuilder()
                .verificationCode(verificationCode)
                .build();
        Mockito.when(userRepository.findByVerificationCode(verificationCode))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        userServiceImpl.verifyAccount(verificationCode);

        Assertions.assertTrue(user.getEnable());
    }

    @Test
    void verifyAccountThrowsException() {
        String verificationCode = "12345678910";
        User user = DataProvider.userBuilder()
                .verificationCode(verificationCode)
                .build();
        Mockito.when(userRepository.findByVerificationCode(verificationCode))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userServiceImpl.verifyAccount("128991740"));
    }

    @Test
    void resetPasswordProcessDoesNotThrowException() {
        String email = "test@test.com";
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));
        user.setPassword("12345");
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Assertions.assertDoesNotThrow(() -> userServiceImpl.resetPasswordProcess(user));
    }

    @Test
    void resetPasswordShouldNotWork() {
        String email = "test@test.com";
        User user = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(new User()));
        user.setPassword("12345");
        Mockito.when(userRepository.save(user))
                .thenReturn(new User());

        String currentPassword = user.getPassword();

        userServiceImpl.resetPasswordProcess(user);

        String changedPassword = user.getPassword();
        Assertions.assertEquals(currentPassword, changedPassword);
    }

    @Test
    void isNotPositiveBalanceOrExistsDigitalSignatureShouldWork() {
        User currentUser = DataProvider.userBuilder().build();
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(-1L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(BigDecimal.valueOf(100_000L))
                .build();

        Assertions.assertTrue(userServiceImpl.isNotPositiveBalanceOrExistsDigitalSignature(currentUser,
                targetUser, realtyObject));
    }

    @Test
    void isNotPositiveBalanceOrExistsDigitalSignatureThrowsException() {
        User currentUser = DataProvider.userBuilder()
                .balance(null)
                .build();
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(10_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(BigDecimal.valueOf(100_000L))
                .build();

        Assertions.assertThrows(NullPointerException.class, () -> userServiceImpl
                .isNotPositiveBalanceOrExistsDigitalSignature(currentUser, targetUser, realtyObject));
    }

    @Test
    void isNotPositiveBalanceOrExistsDigitalSignature_whenCurrentUserDigitalSignatureIsNull() {
        User currentUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(100_000L))
                .digitalSignature(null)
                .build();
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(10_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(BigDecimal.valueOf(100_000L))
                .build();

        Assertions.assertEquals(true, userServiceImpl
                .isNotPositiveBalanceOrExistsDigitalSignature(
                        currentUser,
                        targetUser,
                        realtyObject
                ));
    }
}
