package com.infy.card.management.system.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.infy.card.management.system.enums.CardStatus;
import com.infy.card.management.system.enums.CardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size( max = 100)
    @NotBlank(message = "cardHolderName should not be blank")
    private String cardNumber;


    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotNull
    @Future
    @JsonFormat(pattern = "ddMMyyyy")
    private LocalDate expiryDate;

    @NotNull
    @Min(0)
    @Max(100)
    private Long dailyLimit;


    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
}
