package ru.realty.erealty.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import ru.realty.erealty.dto.SimpleMailMessageDto;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMailMessageMapperTest extends BaseSpringBootTest {
    private final SimpleMailMessageMapper simpleMailMessageMapper = new SimpleMailMessageMapperImpl();

    @Test
    void fromSimpleMailMessageDtoShouldWork() {
        SimpleMailMessageDto simpleMailMessageDto = DataProvider.simpleMailMessageDtoBuilder().build();

        SimpleMailMessage simpleMailMessage = simpleMailMessageMapper
                .fromSimpleMailMessageDto(simpleMailMessageDto);

        assertThat(simpleMailMessage)
                .isNotNull();
    }

    @Test
    void fromSimpleMailMessageDtoShouldNotWork() {
        SimpleMailMessage actualSimpleMailMessage = new SimpleMailMessage();
        SimpleMailMessageDto simpleMailMessageDto = DataProvider.simpleMailMessageDtoBuilder()
                .build();

        SimpleMailMessage expectedSimpleMailMessage = simpleMailMessageMapper
                .fromSimpleMailMessageDto(simpleMailMessageDto);

        assertThat(actualSimpleMailMessage)
                .isNotEqualTo(expectedSimpleMailMessage);
    }

    @Test
    void fromSimpleMailMessageDtoShouldWorkWithReturnNull() {
        SimpleMailMessage simpleMailMessage = simpleMailMessageMapper
                .fromSimpleMailMessageDto(null);

        assertThat(simpleMailMessage)
                .isNull();
    }
}
