package com.infy.card.management.system.dto;


import com.infy.card.management.system.enums.KycStatus;
import com.infy.card.management.system.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private KycStatus kycStatus;
    private String pan;
    private Title title;
    private AddressResponse address;

}
