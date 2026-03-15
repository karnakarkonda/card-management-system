package com.infy.card.management.system.service;


import com.infy.card.management.system.Model.Address;
import com.infy.card.management.system.Model.User;
import com.infy.card.management.system.dto.AddressRequest;
import com.infy.card.management.system.exceptions.UserNotFoundException;
import com.infy.card.management.system.repository.AddressRepo;
import com.infy.card.management.system.repository.UserRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressService {

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressService(AddressRepo addressRepo, UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    public Address saveAddress(AddressRequest addressRequest) {
        log.info("Request received for saving address for userId: {}", addressRequest.getUserId());
        User fetchedUser = userRepo.findById(addressRequest.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found with userId: {}", addressRequest.getUserId());
                    throw new UserNotFoundException(
                            "User details are not present for the userId: "
                                    + addressRequest.getUserId()
                                    + ". Please insert user details first."
                    );
                });
        log.info("User found with userId: {}. Proceeding to save address.", addressRequest.getUserId());

        Address address = Address.builder()
                .line1(addressRequest.getLine1())
                .line2(addressRequest.getLine2())
                .line3(addressRequest.getLine3())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .country(addressRequest.getCountry())
                .pincode(addressRequest.getPincode())
                .user(fetchedUser)
                .build();

        Address savedAddress = addressRepo.save(address);
        log.info("Address saved successfully with addressId: {}", savedAddress.getAddressId());
        return savedAddress;
    }
}
