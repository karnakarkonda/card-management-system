package com.infy.card.management.system.service;


import com.infy.card.management.system.Model.Address;
import com.infy.card.management.system.Model.User;
import com.infy.card.management.system.dto.AddressResponse;
import com.infy.card.management.system.dto.UserDetailsResponse;
import com.infy.card.management.system.dto.UserRequest;
import com.infy.card.management.system.enums.KycStatus;
import com.infy.card.management.system.exceptions.UserAlreadyExists;
import com.infy.card.management.system.exceptions.UserNotFoundException;
import com.infy.card.management.system.repository.AddressRepo;
import com.infy.card.management.system.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    AddressResponse updatedAddress;

    public UserService(UserRepo userRepo, AddressRepo addressRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser(UserRequest request) {

        log.info("request received for saving user with phone number: {}", request.getPhoneNumber());
        userRepo.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(existingUser -> {
                    log.error("User already exists with phone number: {}", request.getPhoneNumber());
                    throw new UserAlreadyExists(
                            "User already exists with the phone number: " + request.getPhoneNumber()
                    );
                });
        User user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .title(request.getTitle())
                .name(request.getName())
                .pan(request.getPan())
                .kycStatus(KycStatus.NOT_DONE)
                .build();

        User savedUser = userRepo.save(user);
        log.info("user saved successfully with userId: {}", savedUser.getUserId());
        return savedUser;
    }

    public UserDetailsResponse getUserById(Integer userId) {
        log.info("request received for fetching user details with userId: {}", userId);
        User fetchedUser = userRepo.findById(userId).orElseThrow(() -> {
            log.error("User not found with userId: {}", userId);
            throw new UserNotFoundException("User details are not present for the given userId: " + userId);
        });
        log.info("User details fetched successfully for userId: {}", userId);

        Address address = addressRepo.findByUserUserId(userId);
        if (address != null) {
            log.warn("Address details found for userId: {}", userId);
            updatedAddress = AddressResponse.builder()
                    .addressId(address.getAddressId())
                    .userId(address.getUser().getUserId())
                    .line1(address.getLine1())
                    .line2(address.getLine2())
                    .line3(address.getLine3())
                    .city(address.getCity())
                    .state(address.getState())
                    .country(address.getCountry())
                    .pincode(address.getPincode())
                    .build();
        } else {
            log.info("Address details not found for userId: {}", userId);
            updatedAddress = AddressResponse.builder()
                    .addressId(null)
                    .line1(null)
                    .line2(null)
                    .line3(null)
                    .city(null)
                    .state(null)
                    .country(null)
                    .pincode(null)
                    .build();
        }
        return UserDetailsResponse.builder()
                .userId(Long.valueOf(fetchedUser.getUserId()))
                .phoneNumber(fetchedUser.getPhoneNumber())
                .title(fetchedUser.getTitle())
                .name(fetchedUser.getName())
                .pan(fetchedUser.getPan())
                .kycStatus(fetchedUser.getKycStatus())
                .address(updatedAddress)
                .build();
    }
}
