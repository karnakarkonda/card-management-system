package com.infy.card.management.system.controller;


import com.infy.card.management.system.Model.Address;
import com.infy.card.management.system.dto.AddressRequest;
import com.infy.card.management.system.dto.AddressResponse;
import com.infy.card.management.system.response.ApiResponse;
import com.infy.card.management.system.service.AddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/address")
public class AddressController {

    private final ModelMapper modelMapper;
    private final AddressService addressService;

    public AddressController(ModelMapper modelMapper, AddressService addressService) {
        this.modelMapper = modelMapper;
        this.addressService = addressService;
    }


    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveAddress(@Valid @RequestBody AddressRequest addressRequest) {
        log.info("POST /api/v1/address called for userId: {}", addressRequest.getUserId());
        Address savedAddress = addressService.saveAddress(addressRequest);
        return ResponseEntity.ok().body(new ApiResponse("Address saved successfully with addressId: " + savedAddress.getAddressId(), modelMapper.map(savedAddress, AddressResponse.class)));
    }

}
