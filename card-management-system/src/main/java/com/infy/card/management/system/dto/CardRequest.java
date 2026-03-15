package com.infy.card.management.system.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.infy.card.management.system.enums.CardType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {

    @NotNull(message = "userId is required")
    private Integer userId;

    @NotNull(message = "cardNumber should not be null")
    @Size(min = 12, max = 16)
    private String cardNumber;

    @NotNull(message = "cardType is required")
    private CardType cardType;

    @Future(message = "expiryDate must be in future")
    @JsonFormat(pattern = "ddMMyyyy")
    private LocalDate expiryDate;

    @NotNull(message = "dailyLimit should not be null")
    private Long dailyLimit;
}
