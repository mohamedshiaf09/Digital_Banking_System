package com.bank.customerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Mobile must contain 10 digits")
    private String mobile;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "\\d{12}", message = "Aadhaar must contain 12 digits")
    private String aadhaarNumber;
}