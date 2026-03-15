package com.infy.card.management.system.controller;


import com.infy.card.management.system.dto.CardRequest;
import com.infy.card.management.system.dto.CardResponse;
import com.infy.card.management.system.response.ApiResponse;
import com.infy.card.management.system.service.CardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@Slf4j
public class CardController {

    private final CardService cardService;
    private final ModelMapper modelMapper;

    public CardController(CardService cardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveCard(@Valid @RequestBody CardRequest cardRequest) {
        log.info("POST /api/cards/save called for saving card details");

        return ResponseEntity.ok().body(new ApiResponse("Card saved successfully", modelMapper.map(cardService.saveCard(cardRequest), CardResponse.class)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getCardByUserId(@PathVariable Integer userId) {
        log.info("GET /api/cards/{} called for fetching card details", userId);
        List<CardResponse> responseList = cardService.getCardByUserId(userId)
                .stream()
                .map(card -> modelMapper.map(card, CardResponse.class))
                .toList();
        return ResponseEntity.ok().body(new ApiResponse("Card details fetched successfully for userId: " + userId, responseList));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCards() {
        log.info("GET /api/cards called for fetching all card details");
        List<CardResponse> responseList = cardService.getAllCards()
                .stream()
                .map(card -> modelMapper.map(card, CardResponse.class))
                .toList();
        return ResponseEntity.ok().body(new ApiResponse("All card details fetched successfully", responseList));
    }
}
