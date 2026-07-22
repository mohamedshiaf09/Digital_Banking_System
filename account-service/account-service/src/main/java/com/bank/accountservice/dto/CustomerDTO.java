package com.bank.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO is used only to receive the JSON response
 * from Customer Service (via RestTemplate) when validating
 * that a customerId exists.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long customerId;
    private String customerName;
    private String email;
    private String mobile;
    private String address;
    private String aadhaarNumber;
}
