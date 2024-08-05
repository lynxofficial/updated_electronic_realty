package ru.realty.erealty.dto;

import lombok.Builder;

@Builder
public record AgencyResponse(Integer id,
                             String name,
                             String address) {
}
