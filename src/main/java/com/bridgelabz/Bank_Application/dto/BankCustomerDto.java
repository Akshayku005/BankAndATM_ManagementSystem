package com.bridgelabz.Bank_Application.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public @Data class BankCustomerDto {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "User Name is Not valid")
    private String accountHolderName;
    @NotEmpty(message = "Address should not be empty !!")
    private String address;
    @Pattern(regexp = "[7 8 9][0-9]{9}", message = "Invalid number, phone number should contain 10 digits")
    private String phoneNo;
    @Email(message = "Insert valid email")
    private String email;
    @NotEmpty(message = "AadharNumber should not be empty !!")
    private String aadharNumber;
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "date of birth must be in DD-MM-YYYY format")
    private String dob;
}
