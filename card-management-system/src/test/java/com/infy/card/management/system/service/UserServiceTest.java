package com.infy.card.management.system.service;

import com.infy.card.management.system.Model.Address;
import com.infy.card.management.system.Model.User;
import com.infy.card.management.system.dto.UserDetailsResponse;
import com.infy.card.management.system.dto.UserRequest;
import com.infy.card.management.system.enums.Title;
import com.infy.card.management.system.exceptions.UserAlreadyExists;
import com.infy.card.management.system.exceptions.UserNotFoundException;
import com.infy.card.management.system.repository.AddressRepo;
import com.infy.card.management.system.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private AddressRepo addressRepo;

    @Test
    void shouldSaveUserSuccessfully() {

        User user = new User();
        user.setUserId(1);
        user.setPhoneNumber("1234567890");

        UserRequest request = new UserRequest();
        request.setPhoneNumber("1234567890");
        request.setPassword("password");
        request.setTitle(Title.Mr);
        request.setName("John Doe");
        request.setPan("ABCDE1234F");

        when(userRepo.findByPhoneNumber(any())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(request);

        Assertions.assertEquals(1, savedUser.getUserId());
        Assertions.assertEquals("1234567890", savedUser.getPhoneNumber());
        verify(userRepo, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        User user = new User();
        user.setUserId(1);
        user.setPhoneNumber("1234567890");

        UserRequest request = new UserRequest();
        request.setPhoneNumber("1234567890");

        when(userRepo.findByPhoneNumber(any())).thenReturn(Optional.of(user));

        UserAlreadyExists exception = Assertions.assertThrows(UserAlreadyExists.class, () -> {
            userService.saveUser(request);
        });

        Assertions.assertEquals(
                "User already exists with the phone number: " + request.getPhoneNumber(),
                exception.getMessage()
        );
        verify(userRepo, never()).save(any());
    }

    @Test
    void shouldFetchUserDetailsByUserIdSuccessfully() {

        User user = new User(1, "1234567890", "encodedPassword",
                Title.Mr, "John Doe", "ABCDE1234F", null, null, null);

        Address address = new Address();
        address.setAddressId(1L);
        address.setLine1("line1");
        address.setLine2("line2");
        address.setLine3("line3");
        address.setCity("city");
        address.setState("state");
        address.setCountry("country");
        address.setPincode("123456");
        address.setUser(user);

        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(addressRepo.findByUserUserId(anyInt())).thenReturn(address);

        UserDetailsResponse fetchedResponse = userService.getUserById(1);

        Assertions.assertEquals(1L, fetchedResponse.getUserId());
        Assertions.assertEquals("1234567890", fetchedResponse.getPhoneNumber());
        Assertions.assertEquals("city", fetchedResponse.getAddress().getCity());

        verify(userRepo, times(1)).findById(anyInt());
        verify(addressRepo, times(1)).findByUserUserId(anyInt());
    }


    @Test
    void shouldFetchUserDetailsByUserIdWhenAddressNotFound() {

        User user = new User(1, "1234567890", "encodedPassword",
                Title.Mr, "John Doe", "ABCDE1234F", null, null, null);

        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(addressRepo.findByUserUserId(anyInt())).thenReturn(null);

        UserDetailsResponse fetchedResponse = userService.getUserById(1);

        Assertions.assertEquals(1L, fetchedResponse.getUserId());
        Assertions.assertEquals("1234567890", fetchedResponse.getPhoneNumber());
        Assertions.assertNull(fetchedResponse.getAddress().getCity());

        verify(userRepo, times(1)).findById(anyInt());
        verify(addressRepo, times(1)).findByUserUserId(anyInt());
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1);
        });

        Assertions.assertEquals(
                "User details are not present for the given userId: " + 1,
                exception.getMessage()
        );
        verify(userRepo, times(1)).findById(anyInt());

    }
}