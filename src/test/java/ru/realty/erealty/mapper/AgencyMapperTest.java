package ru.realty.erealty.mapper;

import org.junit.jupiter.api.Test;
import ru.realty.erealty.dto.AgencyResponse;
import ru.realty.erealty.entity.Agency;
import ru.realty.erealty.util.DataProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AgencyMapperTest {
    private final AgencyMapper agencyMapper = new AgencyMapperImpl();

    @Test
    void toAgencyResponseShouldWork() {
        Agency agency = DataProvider.agencyBuilder().build();
        AgencyResponse actualAgencyResponse = DataProvider.agencyResponseBuilder().build();

        AgencyResponse expectedAgencyResponse = agencyMapper.toAgencyResponse(agency);

        assertThat(actualAgencyResponse)
                .isEqualTo(expectedAgencyResponse);
    }

    @Test
    void toAgencyResponseShouldNotWork() {
        Agency agency = DataProvider.agencyBuilder().build();
        AgencyResponse actualAgencyResponse = DataProvider.agencyResponseBuilder()
                .id(10)
                .build();

        AgencyResponse expectedAgencyResponse = agencyMapper.toAgencyResponse(agency);

        assertThat(actualAgencyResponse)
                .isNotEqualTo(expectedAgencyResponse);
    }

    @Test
    void toAgencyResponseListShouldWork() {
        List<Agency> agencies = List.of(
                DataProvider.agencyBuilder().build(),
                DataProvider.agencyBuilder().build()
        );
        List<AgencyResponse> actualAgencyResponses = List.of(
                DataProvider.agencyResponseBuilder().build(),
                DataProvider.agencyResponseBuilder().build()
        );

        List<AgencyResponse> expectedAgencyResponses = agencyMapper.toAgencyResponseList(agencies);

        assertThat(actualAgencyResponses)
                .isEqualTo(expectedAgencyResponses);
    }

    @Test
    public void toAgencyResponseListShouldNotWork() {
        List<Agency> agencies = List.of(
                DataProvider.agencyBuilder().build(),
                DataProvider.agencyBuilder().build()
        );
        List<AgencyResponse> actualAgencyResponses = List.of(
                DataProvider.agencyResponseBuilder().build(),
                DataProvider.agencyResponseBuilder().build(),
                DataProvider.agencyResponseBuilder().build()
        );

        List<AgencyResponse> expectedAgencyResponses = agencyMapper.toAgencyResponseList(agencies);

        assertThat(actualAgencyResponses)
                .isNotEqualTo(expectedAgencyResponses);
    }
}
