package ru.realty.erealty.service.user.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.util.DataProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

public class UserServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        List<User> users = userServiceImpl.findAll();

        assertThat(users)
                .isEqualTo(userRepository.findAll());
    }

    @Test
    void findAllShouldThrowsException() {
        when(userRepository.findAll())
                .thenReturn(List.of(
                        DataProvider.userBuilder().build(),
                        DataProvider.userBuilder().build()
                ));

        List<User> users = userServiceImpl.findAll();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> users.add(DataProvider.userBuilder().build()));
    }

    @Test
    void findByEmailShouldWork() {
        String email = UserEmail.DEFAULT_EMAIL;
        User user = DataProvider.userBuilder().build();
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        assertThat(userServiceImpl.findByEmail(email))
                .isNotNull();
    }

    @Test
    void findByEmailThrowsException() {
        String email = UserEmail.DEFAULT_EMAIL;
        User user = DataProvider.userBuilder().build();
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userServiceImpl.findByEmail(null));
    }

    @Test
    void deleteByIdDoesNotThrowException() {
        User user = DataProvider.userBuilder().build();
        Mockito.doNothing()
                .when(userRepository)
                .deleteById(user.getId());

        assertThatNoException()
                .isThrownBy(() -> userServiceImpl.deleteById(user.getId()));
    }

    @Test
    void saveUserShouldWork() {
        User user = DataProvider.userBuilder()
                .password("12345")
                .build();
        when(userRepository.save(user))
                .thenReturn(user);

        String url = "test.com";

        userServiceImpl.saveUser(user, url);

        assertThat(user.getEnable())
                .isFalse();
    }

    @Test
    void saveUserThrowsException() {
        User user = DataProvider.userBuilder()
                .password("1234567")
                .build();
        when(userRepository.save(user))
                .thenReturn(user);

        String url = "test.com";

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userServiceImpl.saveUser(new User(), url));
    }

    @Test
    void saveUserWithHttpSessionAndHttpServletRequestShouldWork() {
        User user = DataProvider.userBuilder()
                .password("12345")
                .build();
        when(userRepository.save(user))
                .thenReturn(user);

        HttpSession httpSession = new MockHttpSession();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();

        userServiceImpl.saveUser(user, httpSession, httpServletRequest);

        assertThat(httpSession.getAttribute("msg"))
                .isNotNull();
    }

    @Test
    void saveUserWithHttpSessionAndHttpServletRequestThrowsException() {
        User user = DataProvider.userBuilder()
                .password(null)
                .build();
        when(userRepository.save(user))
                .thenReturn(user);

        HttpSession httpSession = new MockHttpSession();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userServiceImpl.saveUser(user, httpSession, httpServletRequest));
    }

    @Test
    void removeSessionMessageThrowsExceptionException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> userServiceImpl.removeSessionMessage());
    }

    @Test
    void hasExpiredShouldWork() {
        LocalDateTime localDateTime = LocalDateTime.now();

        assertThat(userServiceImpl.hasExpired(localDateTime))
                .isFalse();
    }

    @Test
    void hasExpiredShouldNotWork() {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(45);

        assertThat(userServiceImpl.hasExpired(localDateTime))
                .isTrue();
    }

    @Test
    void verifyAccountShouldWork() {
        String verificationCode = "12345";
        User user = DataProvider.userBuilder()
                .verificationCode(verificationCode)
                .build();

        when(userRepository.findByVerificationCode(verificationCode))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user))
                .thenReturn(user);

        userServiceImpl.verifyAccount(verificationCode);

        assertThat(user.getEnable())
                .isTrue();
    }

    @Test
    void verifyAccountThrowsException() {
        String verificationCode = "12345678910";
        User user = DataProvider.userBuilder()
                .verificationCode(verificationCode)
                .build();

        when(userRepository.findByVerificationCode(verificationCode))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user))
                .thenReturn(user);

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userServiceImpl.verifyAccount("128991740"));
    }

    @Test
    void resetPasswordProcessDoesNotThrowException() {
        String email = UserEmail.DEFAULT_EMAIL;
        User user = DataProvider.userBuilder().build();

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        user.setPassword("12345");

        when(userRepository.save(user))
                .thenReturn(user);

        assertThatNoException()
                .isThrownBy(() -> userServiceImpl.resetPasswordProcess(user));
    }

    @Test
    void resetPasswordShouldNotWork() {
        String email = UserEmail.DEFAULT_EMAIL;
        User user = DataProvider.userBuilder().build();

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(new User()));

        user.setPassword("12345");

        when(userRepository.save(user))
                .thenReturn(new User());

        String currentPassword = user.getPassword();

        userServiceImpl.resetPasswordProcess(user);

        String changedPassword = user.getPassword();
        assertThat(changedPassword)
                .isEqualTo(currentPassword);
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

        assertThat(userServiceImpl.isNotPositiveBalanceOrExistsDigitalSignature(
                        currentUser,
                        targetUser,
                        realtyObject))
                .isTrue();
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

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> userServiceImpl
                        .isNotPositiveBalanceOrExistsDigitalSignature(currentUser, targetUser, realtyObject));
    }

    @Test
    void isNotPositiveBalanceOrExistsDigitalSignatureWhenCurrentUserDigitalSignatureIsNull() {
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

        assertThat(userServiceImpl.isNotPositiveBalanceOrExistsDigitalSignature(
                        currentUser,
                        targetUser,
                        realtyObject
                ))
                .isTrue();
    }

    @Test
    void isNotPositiveBalanceOrExistsDigitalSignatureWhenCurrentUserIdEqualsTargetUserId() {
        User currentUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(100_000L))
                .digitalSignature("1234567891011")
                .build();
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(10_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(BigDecimal.valueOf(100_000L))
                .build();

        assertThat(userServiceImpl.isNotPositiveBalanceOrExistsDigitalSignature(
                        currentUser,
                        targetUser,
                        realtyObject
                ))
                .isTrue();
    }
}
