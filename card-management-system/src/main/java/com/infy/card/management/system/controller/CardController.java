package com.infy.card.management.system.controller;


import com.infy.card.management.system.dto.CardRequest;
import com.infy.card.management.system.dto.CardResponse;
import com.infy.card.management.system.response.ApiResponse;
import com.infy.card.management.system.service.CardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
