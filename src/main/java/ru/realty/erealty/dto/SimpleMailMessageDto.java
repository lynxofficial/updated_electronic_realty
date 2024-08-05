package ru.realty.erealty.dto;

import lombok.Builder;

@Builder
public record SimpleMailMessageDto(String from,
                                   String[] to,
                                   String subject,
                                   String text) {
}
