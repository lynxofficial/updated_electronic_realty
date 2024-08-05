package ru.realty.erealty.mapper;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.dto.MimeMessageHelperDto;
import ru.realty.erealty.entity.support.MimeMessageHelperSupport;
import ru.realty.erealty.support.BaseSpringBootTest;
import ru.realty.erealty.util.DataProvider;

import static org.assertj.core.api.Assertions.assertThat;

class MimeMessageHelperMapperTest extends BaseSpringBootTest {
    private final MimeMessageHelperMapper mimeMessageHelperMapper = new MimeMessageHelperMapperImpl();

    @Test
    void fromMimeMessageHelperDtoShouldWork() {
        MimeMessageHelperDto mimeMessageHelperDto = DataProvider.mimeMessageHelperDtoBuilder()
                .build();

        MimeMessageHelperSupport mimeMessageHelperSupport = mimeMessageHelperMapper
                .fromMimeMessageHelperDto(mimeMessageHelperDto);

        assertThat(mimeMessageHelperSupport)
                .isNotNull();
    }

    @Test
    void fromMimeMessageHelperDtoShouldNotWork() {
        MimeMessageHelperSupport actualMimeMessageHelperSupport = DataProvider.mimeMessageHelperSupportBuilder()
                .build();
        MimeMessageHelperDto mimeMessageHelperDto = DataProvider.mimeMessageHelperDtoBuilder()
                .build();

        MimeMessageHelperSupport expectedMimeMessageHelperSupport = mimeMessageHelperMapper
                .fromMimeMessageHelperDto(mimeMessageHelperDto);

        assertThat(actualMimeMessageHelperSupport)
                .isNotEqualTo(expectedMimeMessageHelperSupport);
    }

    @Test
    void fromMimeMessageHelperDtoShouldWorkWithReturnNull() {
        MimeMessageHelperSupport mimeMessageHelperSupport = mimeMessageHelperMapper
                .fromMimeMessageHelperDto(null);

        assertThat(mimeMessageHelperSupport)
                .isNull();
    }
}
