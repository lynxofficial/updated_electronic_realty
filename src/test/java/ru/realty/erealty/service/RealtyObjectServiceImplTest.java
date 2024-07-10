package ru.realty.erealty.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class RealtyObjectServiceImplTest extends BaseSpringBootTest {
    @Test
    void findAllShouldWork() {
        Mockito.when(realtyObjectRepository.findAll())
                .thenReturn(List.of(new RealtyObject(), new RealtyObject()));
        List<RealtyObject> realtyObjects = realtyObjectServiceImpl.findAll();
        Assertions.assertEquals(realtyObjects, realtyObjectRepository.findAll());
    }

    @SneakyThrows
    @Test
    void buyRealtyObjectShouldWork() {
        Mockito.when(realtyObjectRepository.findById(0))
                .thenReturn(Optional.of(new RealtyObject()));
        RealtyObject realtyObject = realtyObjectServiceImpl.buyRealtyObject("0");
        Assertions.assertEquals(realtyObject, realtyObjectRepository.findById(0).orElseThrow());
    }

    @SneakyThrows
    @Test
    void sellRealtyObjectShouldWork() {
        User user = new User();
        user.setFullName("test test test");
        user.setVerificationCode("12345");
        user.setEmail("test@test.com");
        RealtyObject realtyObject = new RealtyObject();
        realtyObject.setPrice(BigDecimal.valueOf(100_000L));
        Mockito.when(realtyObjectRepository.save(realtyObject))
                .thenReturn(realtyObject);
        MultipartFile multipartFile = new MockMultipartFile("image610743684540901600tmp.png",
                "image610743684540901600tmp.png", "text/plain",
                new FileInputStream("src/main/resources/images/image610743684540901600tmp.png"));
        realtyObjectServiceImpl.sellRealtyObject(user, realtyObject, multipartFile);
        Assertions.assertEquals(realtyObject.getUser(), user);
    }

    @SneakyThrows
    @Test
    void buyRealtyObjectWithDigitalSignatureThrowsException() {
        RealtyObject realtyObject = new RealtyObject();
        realtyObject.setUser(new User());
        realtyObject.getUser().setBalance(BigDecimal.valueOf(1_000_000L));
        realtyObject.getUser().setEmail("test@test.com");
        Mockito.when(realtyObjectRepository.findById(realtyObject.getRealtyObjectId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.when(userRepository.findById(realtyObject.getUser().getUserId()))
                .thenReturn(Optional.ofNullable(realtyObject.getUser()));
        User targetUser = new User();
        targetUser.setEmail("test@test.com");
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

    @SneakyThrows
    @Test
    void deleteRealtyObjectThrowsException() {
        RealtyObject realtyObject = new RealtyObject();
        realtyObject.setUser(new User());
        Mockito.when(realtyObjectRepository.findById(realtyObject.getRealtyObjectId()))
                .thenReturn(Optional.of(realtyObject));
        Mockito.doNothing()
                .when(realtyObjectRepository)
                .delete(realtyObject);
        Assertions.assertThrows(NullPointerException.class,
                () -> realtyObjectServiceImpl.deleteRealtyObject(realtyObject));
    }
}
