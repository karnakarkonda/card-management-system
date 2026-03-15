package com.infy.card.management.system.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {


    @NotNull(message = "userId should not be null")
    private Integer userId;

    @NotBlank(message = "line1 should not be blank")
    @Size(max = 30)
    private String line1;

    @Size(max = 30)
    private String line2;

    @Size(max = 30)
    private String line3;

    @NotBlank(message = "pincode should not be blank")
    @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
    private String pincode;

    @NotBlank(message = "city should not be blank")
    @Size(max = 30)
    private String city;

    @NotBlank(message = "state should not be blank")
    @Size(max = 30)
    private String state;

    @NotBlank(message = "country should not be blank")
    @Size(max = 30)
    private String country;
}
