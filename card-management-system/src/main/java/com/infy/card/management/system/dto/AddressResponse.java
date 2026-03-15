package com.infy.card.management.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long addressId;
    private Integer userId;

    private String line1;
    private String line2;
    private String line3;

    private String pincode;
    private String city;
    private String state;
    private String country;
}
