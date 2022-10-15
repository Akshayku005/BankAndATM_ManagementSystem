package com.bridgelabz.Bank_Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class CustomerBalance {
    private Long AccountNumber;
    private double totalBalance;
}
