package ru.realty.erealty.mapper;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.dto.RealtyObjectResponse;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.util.DataProvider;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RealtyObjectMapperTest {
    private final RealtyObjectMapper realtyObjectMapper = new RealtyObjectMapperImpl();

    @Test
    void toRealtyObjectResponseShouldWork() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        RealtyObjectResponse actualRealtyObjectResponse = DataProvider.realtyObjectResponseBuilder().build();

        RealtyObjectResponse expectedRealtyObjectResponse = realtyObjectMapper.toRealtyObjectResponse(realtyObject);

        assertThat(actualRealtyObjectResponse)
                .isEqualTo(expectedRealtyObjectResponse);
    }

    @Test
    void toRealtyObjectResponseShouldNotWork() {
        RealtyObject realtyObject = DataProvider.realtyObjectBuilder().build();
        RealtyObjectResponse actualRealtyObjectResponse = DataProvider.realtyObjectResponseBuilder()
                .id(101)
                .build();

        RealtyObjectResponse expectedRealtyObjectResponse = realtyObjectMapper.toRealtyObjectResponse(realtyObject);

        assertThat(actualRealtyObjectResponse)
                .isNotEqualTo(expectedRealtyObjectResponse);
    }

    @Test
    void toRealtyObjectResponseListShouldWork() {
        List<RealtyObject> realtyObjects = new ArrayList<>();
        realtyObjects.add(DataProvider.realtyObjectBuilder().build());
        realtyObjects.add(DataProvider.realtyObjectBuilder().build());
        List<RealtyObjectResponse> actualRealtyObjectResponses = List.of(
                DataProvider.realtyObjectResponseBuilder().build(),
                DataProvider.realtyObjectResponseBuilder().build()
        );

        List<RealtyObjectResponse> expectedRealtyObjectResponses = realtyObjectMapper
                .toRealtyObjectResponseList(realtyObjects);

        assertThat(actualRealtyObjectResponses)
                .isEqualTo(expectedRealtyObjectResponses);
    }

    @Test
    void toRealtyObjectResponseListShouldNotWork() {
        List<RealtyObject> realtyObjects = List.of(
                DataProvider.realtyObjectBuilder().build(),
                DataProvider.realtyObjectBuilder().build()
        );
        List<RealtyObjectResponse> actualRealtyObjectResponses = List.of(
                DataProvider.realtyObjectResponseBuilder().build(),
                DataProvider.realtyObjectResponseBuilder().build(),
                DataProvider.realtyObjectResponseBuilder().build()
        );

        List<RealtyObjectResponse> expectedRealtyObjectResponses = realtyObjectMapper
                .toRealtyObjectResponseList(realtyObjects);

        assertThat(actualRealtyObjectResponses)
                .isNotEqualTo(expectedRealtyObjectResponses);
    }
}
