package com.infy.card.management.system.dto;

import com.infy.card.management.system.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer userId;

    private Long phoneNumber;

    private Title title;

    private String name;

    private String pan;

    private String kycStatus;

}
