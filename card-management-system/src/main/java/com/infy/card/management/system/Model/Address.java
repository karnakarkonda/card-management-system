package com.infy.card.management.system.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses1")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(min = 1, max = 30)
    @NotBlank(message = "line1 should not be blank")
    private String line1;

    @Size(max = 30)
    private String line2;

    @Size(max = 30)
    private String line3;


    @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
    private String pincode;

    @Size(min = 1, max = 30)
    @NotBlank(message = "city should not be blank")
    private String city;

    @Size(min = 1, max = 30)
    @NotBlank(message = "state should not be blank")
    private String state;

    @Size(min = 1, max = 30)
    @NotBlank(message = "country should not be blank")
    private String country;
}
