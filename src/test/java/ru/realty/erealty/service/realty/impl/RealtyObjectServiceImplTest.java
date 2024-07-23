package ru.realty.erealty.service.realty.impl;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.util.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class RealtyObjectServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        when(realtyObjectRepository.findAll())
                .thenReturn(List.of(DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();

        assertThat(realtyObjectRepository.findAll())
                .isEqualTo(realtyObjects);
    }

    @Test
    void findAllThrowsException() {
        when(realtyObjectRepository.findAll())
                .thenReturn(List.of(DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> realtyObjects.add(DataProvider.realtyObjectBuilder().build()));
    }

    @Test
    void buyRealtyObjectShouldWork() throws RealtyObjectNotFoundException {
        when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        RealtyObject realtyObject = realtyObjectServiceImpl.buyRealtyObject("0");

        assertThat(realtyObjectRepository.findById(0).orElseThrow())
                .isEqualTo(realtyObject);
    }

    @Test
    void buyRealtyObjectThrowsException() {
        when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        assertThatExceptionOfType(RealtyObjectNotFoundException.class)
                .isThrownBy(() -> realtyObjectServiceImpl.buyRealtyObject("1"));
    }

    @Test
    void sellRealtyObjectShouldWork() throws IOException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("12345")
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(BigDecimal.valueOf(100_000L))
                .build();
        MultipartFile multipartFile = new MockMultipartFile("realtyObjectImageForTest.png",
                "realtyObjectImageForTest.png", "text/plain",
                new FileInputStream("src/main/resources/images/realtyObjectImageForTest.png"));

        when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);

        realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile);

        assertThat(user)
                .isEqualTo(realtyObject.getUser());
    }

    @Test
    void sellRealtyObjectShouldWork_withRealtyObjectPriceIsNull() throws IOException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("12345")
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(null)
                .build();
        MultipartFile multipartFile = new MockMultipartFile("realtyObjectImageForTest.png",
                "realtyObjectImageForTest.png", "text/plain",
                new FileInputStream("src/main/resources/images/realtyObjectImageForTest.png"));

        when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);

        realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile);

        assertThat(user)
                .isEqualTo(realtyObject.getUser());
    }

    @Test
    void sellRealtyObjectThrowsException() throws IOException {
        User user = DataProvider.userBuilder()
                .fullName("test test test")
                .verificationCode("12345")
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .price(BigDecimal.valueOf(100_000L))
                .build();
        MultipartFile multipartFile = new MockMultipartFile("realtyObjectImageForTest.png",
                new FileInputStream("src/main/resources/images/realtyObjectImageForTest.png"));

        when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);

        assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile));
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectWithDigitalSignatureShouldWork() throws RealtyObjectNotFoundException {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .realtyObjects(new ArrayList<>())
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(targetUser)
                .price(BigDecimal.valueOf(1_000_000L))
                .build();
        targetUser.getRealtyObjects().add(realtyObject);

        User currentUser = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();

        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThat(realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()))
                .isTrue();
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectWithDigitalSignatureShouldNotWorkWithCurrentUserBalanceLessThanOne()
            throws RealtyObjectNotFoundException {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(targetUser)
                .build();
        User currentUser = DataProvider.userBuilder().build();

        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThat(realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()))
                .isFalse();
    }

    @Test
    @WithMockUser(username = UserEmail.DEFAULT_EMAIL)
    void buyRealtyObjectWithDigitalSignatureShouldReturnTrueWhenCurrentUserBalanceIsNull()
            throws RealtyObjectNotFoundException {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(targetUser)
                .build();
        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User currentUser = DataProvider.userBuilder()
                .balance(null)
                .build();
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThat(realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()))
                .isTrue();
    }

    @Test
    void buyRealtyObjectWithDigitalSignatureThrowsException() {
        User user = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(user)
                .build();
        User targetUser = DataProvider.userBuilder().build();

        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        when(userRepository.findByEmail(UserEmail.DEFAULT_EMAIL))
                .thenReturn(Optional.of(targetUser));
        when(userRepository.save(targetUser))
                .thenReturn(targetUser);
        when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()));
    }

    @Test
    void deleteRealtyObjectThrowsException() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> realtyObjectServiceImpl.deleteRealtyObject(realtyObject));
    }

    @Test
    void deleteRealtyObjectShouldWorkWithUser() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .build();
        User user = DataProvider.userBuilder()
                .realtyObjects(new ArrayList<>())
                .build();
        realtyObject.setUser(user);
        user.getRealtyObjects().add(realtyObject);
        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThatNoException()
                .isThrownBy(() -> realtyObjectServiceImpl.deleteRealtyObject(realtyObject));
    }

    @Test
    void deleteRealtyObjectShouldNotWorkWithNullUser() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .build();
        User user = DataProvider.userBuilder()
                .realtyObjects(new ArrayList<>())
                .build();
        realtyObject.setUser(null);
        user.getRealtyObjects().add(realtyObject);
        when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> realtyObjectServiceImpl.deleteRealtyObject(realtyObject));
    }
}
