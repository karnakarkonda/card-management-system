package com.infy.card.management.system.dto;

import com.infy.card.management.system.enums.Title;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @NotBlank(message = "phoneNumber should not be blank")
    private String phoneNumber;

    @Size(min = 8, max = 15)
    @NotBlank(message = "password should not be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters and contain at least one letter and one number")
    private String password;

    private Title title;

    @Size(max = 100)
    @NotBlank(message = "name should not be blank")
    private String name;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String pan;
}
