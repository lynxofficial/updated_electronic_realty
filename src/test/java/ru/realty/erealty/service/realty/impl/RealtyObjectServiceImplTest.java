package ru.realty.erealty.service.realty.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;
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

class RealtyObjectServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();

        Assertions.assertThat(realtyObjectRepository.findAll())
                .isEqualTo(realtyObjects);
    }

    @Test
    void findAllThrowsException() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> realtyObjects.add(DataProvider.realtyObjectBuilder().build()));
    }

    @Test
    void buyRealtyObjectShouldWork() throws RealtyObjectNotFoundException {
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        RealtyObject realtyObject = realtyObjectServiceImpl.buyRealtyObject("0");

        Assertions.assertThat(realtyObjectRepository.findById(0).orElseThrow())
                .isEqualTo(realtyObject);
    }

    @Test
    void buyRealtyObjectThrowsException() {
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        Assertions.assertThatExceptionOfType(RealtyObjectNotFoundException.class)
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
        Mockito.when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);

        MultipartFile multipartFile = new MockMultipartFile("realtyObjectImageForTest.png",
                "realtyObjectImageForTest.png", "text/plain",
                new FileInputStream("src/main/resources/images/realtyObjectImageForTest.png"));

        realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile);

        Assertions.assertThat(user)
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
        Mockito.when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);

        MultipartFile multipartFile = new MockMultipartFile("realtyObjectImageForTest.png",
                "realtyObjectImageForTest.png", "text/plain",
                new FileInputStream("src/main/resources/images/realtyObjectImageForTest.png"));

        realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile);

        Assertions.assertThat(user)
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
        Mockito.when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);

        MultipartFile multipartFile = new MockMultipartFile("realtyObjectImageForTest.png",
                new FileInputStream("src/main/resources/images/realtyObjectImageForTest.png"));

        Assertions.assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile));
    }

    @Test
    @WithMockUser(username = "test@test.com")
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
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User currentUser = DataProvider.userBuilder()
                .id(1)
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(currentUser));
        Mockito.when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        Mockito.when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThat(realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()))
                .isTrue();
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void buyRealtyObjectWithDigitalSignatureShouldNotWorkWithCurrentUserBalanceLessThanOne()
            throws RealtyObjectNotFoundException {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(targetUser)
                .build();
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User currentUser = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(currentUser));
        Mockito.when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        Mockito.when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThat(realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()))
                .isFalse();
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void buyRealtyObjectWithDigitalSignatureShouldReturnTrueWhenCurrentUserBalanceIsNull()
            throws RealtyObjectNotFoundException {
        User targetUser = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(targetUser)
                .build();
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User currentUser = DataProvider.userBuilder()
                .balance(null)
                .build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(currentUser));
        Mockito.when(userRepository.save(currentUser))
                .thenReturn(currentUser);
        Mockito.when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThat(realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()))
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
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User targetUser = DataProvider.userBuilder().build();
        Mockito.when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(targetUser));
        Mockito.when(userRepository.save(targetUser))
                .thenReturn(targetUser);
        Mockito.when(userRepository.save(realtyObject.getUser()))
                .thenReturn(realtyObject.getUser());
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject.getId()));
    }

    @Test
    void deleteRealtyObjectThrowsException() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThatExceptionOfType(NullPointerException.class)
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
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThatNoException()
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
        Mockito.when(realtyObjectRepository.findById(realtyObject.getId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> realtyObjectServiceImpl.deleteRealtyObject(realtyObject));
    }
}
