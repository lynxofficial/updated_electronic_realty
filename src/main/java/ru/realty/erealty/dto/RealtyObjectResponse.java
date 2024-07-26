package ru.realty.erealty.dto;

import lombok.Builder;
import ru.realty.erealty.entity.User;

import java.math.BigDecimal;

@Builder
public record RealtyObjectResponse(Integer id,
                                   String name,
                                   String description,
                                   String imageUrl,
                                   BigDecimal price,
                                   Double square,
                                   String address,
                                   User user) {
}
