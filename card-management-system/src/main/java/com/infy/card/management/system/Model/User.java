package com.infy.card.management.system.Model;


import com.infy.card.management.system.enums.KycStatus;
import com.infy.card.management.system.enums.Title;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @NotBlank(message = "phoneNumber should not be blank")
    @Column(unique = true)
    private String phoneNumber;



    @NotBlank(message = "password should not be blank")
    private String password;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Size(min = 0, max = 100)
    @NotBlank(message = "name should not be blank")
    private String name;


    @Size(min = 10, max = 10)
    @NotBlank(message = "pan should not be blank")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    @Column(unique = true)
    private String pan;



    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> cards;
}
