package ru.realty.erealty.util;

import org.springframework.mail.javamail.MimeMessageHelper;
import ru.realty.erealty.constant.UserEmail;
import ru.realty.erealty.constant.UserRole;
import ru.realty.erealty.dto.AgencyResponse;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.dto.RealtyObjectResponse;
import ru.realty.erealty.dto.SimpleMailMessageDto;
import ru.realty.erealty.dto.UserResponse;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.entity.PasswordResetToken;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.entity.support.MimeMessageHelperSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static ru.realty.erealty.constant.UserJavaMailSender.DEFAULT_JAVA_MAIL_SENDER;

public class DataProvider {
    public static Agency.AgencyBuilder<?, ?> agencyBuilder() {
        return Agency.builder()
                .id(1)
                .address("Test district, 0")
                .name("Test Agency");
    }

    public static PasswordResetToken.PasswordResetTokenBuilder<?, ?> passwordResetTokenBuilder() {
        return PasswordResetToken.builder()
                .token("123456")
                .user(userBuilder().build())
                .id(0)
                .expiryDateTime(LocalDateTime.now());
    }

    public static RealtyObject.RealtyObjectBuilder<?, ?> realtyObjectBuilder() {
        return RealtyObject.builder()
                .id(0)
                .name("TestRealtyObject")
                .price(BigDecimal.valueOf(1_000_000L))
                .address("Test district, 10")
                .description("Test description")
                .square(40.9)
                .imageUrl("realtyObjectImageForTest.png")
                .user(userBuilder().build());
    }

    public static User.UserBuilder<?, ?> userBuilder() {
        return User.builder()
                .id(0)
                .password("1234")
                .email(UserEmail.DEFAULT_EMAIL)
                .role(UserRole.USER_ROLE)
                .balance(BigDecimal.ZERO)
                .enable(false)
                .fullName("Test Test Test")
                .passwordForDigitalSignature("123")
                .digitalSignature("DigitalSignature");
    }

    public static AgencyResponse.AgencyResponseBuilder agencyResponseBuilder() {
        return AgencyResponse.builder()
                .id(1)
                .address("Test district, 0")
                .name("Test Agency");
    }

    public static RealtyObjectResponse.RealtyObjectResponseBuilder realtyObjectResponseBuilder() {
        return RealtyObjectResponse.builder()
                .id(0)
                .address("Test district, 10")
                .description("Test description")
                .imageUrl("realtyObjectImageForTest.png")
                .name("TestRealtyObject")
                .square(40.9)
                .user(userBuilder().build())
                .price(BigDecimal.valueOf(1_000_000L));
    }

    public static UserResponse.UserResponseBuilder userResponseBuilder() {
        return UserResponse.builder()
                .id(0)
                .password("1234")
                .passwordForDigitalSignature("123")
                .digitalSignature("DigitalSignature")
                .role("ROLE_USER")
                .balance(BigDecimal.ZERO)
                .fullName("Test Test Test")
                .enable(false)
                .email(UserEmail.DEFAULT_EMAIL);
    }

    public static MimeMessageHelperSupport.MimeMessageHelperSupportBuilder mimeMessageHelperSupportBuilder() {
        return MimeMessageHelperSupport
                .builder()
                .mimeMessageHelper(new MimeMessageHelper(DEFAULT_JAVA_MAIL_SENDER.createMimeMessage()));
    }

    public static MimeMessageHelperDto.MimeMessageHelperDtoBuilder mimeMessageHelperDtoBuilder() {
        return MimeMessageHelperDto.builder()
                .mimeMessage(DEFAULT_JAVA_MAIL_SENDER.createMimeMessage())
                .from("from@test.com")
                .to("to@test.com")
                .subject("subject")
                .content("content")
                .mimeMessageHelper(new MimeMessageHelper(DEFAULT_JAVA_MAIL_SENDER.createMimeMessage()));
    }

    public static SimpleMailMessageDto.SimpleMailMessageDtoBuilder simpleMailMessageDtoBuilder() {
        return SimpleMailMessageDto.builder()
                .text("text")
                .subject("test")
                .to(new String[]{"testString"})
                .from("test from");
    }

    public static User createUserWithBalance(final Integer id) {
        return DataProvider.userBuilder()
                .id(id)
                .balance(BigDecimal.valueOf(100_000L))
                .build();
    }

    public static User createUserWithBalanceAndRealtyObjects(final Integer id) {
        return DataProvider.userBuilder()
                .id(id)
                .balance(BigDecimal.valueOf(100_000L))
                .realtyObjects(new ArrayList<>())
                .build();
    }

    public static User createUserWithBalanceAndRole(
            final Integer id,
            final String role,
            final BigDecimal balance
    ) {
        return DataProvider.userBuilder()
                .id(id)
                .role(role)
                .balance(balance)
                .build();
    }

    public static RealtyObject createRealtyObjectWithUser(final Integer id, final User user) {
        return DataProvider.realtyObjectBuilder()
                .id(id)
                .user(user)
                .build();
    }

    public static RealtyObject createRealtyObjectWithUserAndPrice(
            final Integer id,
            final User user,
            final BigDecimal price
    ) {
        return DataProvider.realtyObjectBuilder()
                .id(id)
                .user(user)
                .price(price)
                .build();
    }

    public static PasswordResetToken createPasswordResetTokenWithExpiryDateTimePlusMinutes(
            final Integer id,
            final Long minutes
    ) {
        return DataProvider.passwordResetTokenBuilder()
                .id(id)
                .expiryDateTime(LocalDateTime.now().plusMinutes(minutes))
                .build();
    }

    public static PasswordResetToken createPasswordResetTokenWithExpiryDateTimeMinutesMinutes(
            final Integer id,
            final Long minutes
    ) {
        return DataProvider.passwordResetTokenBuilder()
                .id(id)
                .expiryDateTime(LocalDateTime.now().minusMinutes(minutes))
                .build();
    }
}
