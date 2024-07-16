package ru.realty.erealty.service.realty.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
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
import java.util.List;
import java.util.Optional;

class RealtyObjectServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();

        Assertions.assertEquals(realtyObjects, realtyObjectRepository.findAll());
    }

    @Test
    void findAllThrowsException() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(DataProvider.realtyObjectBuilder().build(),
                        DataProvider.realtyObjectBuilder().build()));

        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> realtyObjects.add(new RealtyObject()));
    }

    @Test
    void buyRealtyObjectShouldWork() throws RealtyObjectNotFoundException {
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        RealtyObject realtyObject = realtyObjectServiceImpl.buyRealtyObject("0");

        Assertions.assertEquals(realtyObject, realtyObjectRepository.findById(0).orElseThrow());
    }

    @Test
    void buyRealtyObjectThrowsException() {
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(DataProvider.realtyObjectBuilder().build()));

        Assertions.assertThrows(RealtyObjectNotFoundException.class,
                () -> realtyObjectServiceImpl.buyRealtyObject("1"));
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

        Assertions.assertEquals(realtyObject.getUser(), user);
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

        Assertions.assertThrows(AccessDeniedException.class,
                () -> realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile));
    }

    @Test
    void buyRealtyObjectWithDigitalSignatureThrowsException() {
        User user = DataProvider.userBuilder()
                .balance(BigDecimal.valueOf(1_000_000L))
                .build();
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder()
                .user(user)
                .build();
        Mockito.when(realtyObjectRepository.findById(realtyObject.getRealtyObjectId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getUserId()))
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

        Assertions.assertThrows(NullPointerException.class,
                () -> realtyObjectServiceImpl.buyRealtyObjectWithDigitalSignature(realtyObject));
    }

    @Test
    void deleteRealtyObjectThrowsException() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        Mockito.when(realtyObjectRepository.findById(realtyObject.getRealtyObjectId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);

        Assertions.assertThrows(NullPointerException.class,
                () -> realtyObjectServiceImpl.deleteRealtyObject(realtyObject));
    }
}
