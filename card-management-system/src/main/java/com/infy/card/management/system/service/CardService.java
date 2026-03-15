package com.infy.card.management.system.service;


import com.infy.card.management.system.Model.Card;
import com.infy.card.management.system.Model.User;
import com.infy.card.management.system.dto.CardRequest;
import com.infy.card.management.system.enums.CardStatus;
import com.infy.card.management.system.exceptions.UserNotFoundException;
import com.infy.card.management.system.repository.AddressRepo;
import com.infy.card.management.system.repository.CardRepo;
import com.infy.card.management.system.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CardService {

    private final CardRepo cardRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public CardService(CardRepo cardRepo, AddressRepo addressRepo, UserRepo userRepo) {
        this.cardRepo = cardRepo;
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }


    public Card saveCard(CardRequest request) {
        log.info("request received for saving card with card number: {}", request.getCardNumber());
        User fetchedUser = userRepo.findById(request.getUserId()).orElseThrow(() -> {
            log.error("User not found with userId: {}", request.getUserId());
            throw new UserNotFoundException("User details are not present fot the given  userId: " + request.getUserId());
        });

        addressRepo.findById(Long.valueOf(request.getUserId())).orElseThrow(() -> {
            log.error("Address not found for userId: {}", request.getUserId());
            throw new UserNotFoundException("Address details are not present for the given userId: " + request.getUserId());
        });

        if(fetchedUser.getKycStatus().name().equals("NOT_DONE")){
            log.error("KYC not done for userId: {}", request.getUserId());
            throw new UserNotFoundException("KYC is not done for the given userId: " + request.getUserId());
        }

        log.info("Creating card for userId: {}", request.getUserId());
        Card card = Card.builder()
                .cardNumber(request.getCardNumber())
                .cardType(request.getCardType())
                .cardStatus(CardStatus.ACTIVE)
                .expiryDate(request.getExpiryDate())
                .dailyLimit(request.getDailyLimit())
                .user(fetchedUser)
                .build();

        Card savedCard = cardRepo.save(card);
        log.info("Card saved successfully with cardId: {}", savedCard.getCardId());
        return savedCard;
    }
}
