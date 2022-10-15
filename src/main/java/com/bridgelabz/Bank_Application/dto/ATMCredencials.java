package com.bridgelabz.Bank_Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
public @Data class ATMCredencials {
    @NotEmpty(message = "Account Number should not be empty !!")
    private Long accountNumber;
    @NotEmpty(message = "Please enter your ATM pin number")
    private Long atmPin;
}
