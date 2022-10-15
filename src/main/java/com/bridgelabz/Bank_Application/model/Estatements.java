package com.bridgelabz.Bank_Application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "estatement")
@Data
@NoArgsConstructor
public class Estatements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateOfWithDraw;
    private LocalDateTime dateOfDeposite;
    private double moneyDeposited;
    private double moneyWithdrawn;
    private double totalAmount;
    @JsonIgnore
    @ManyToOne
    private BankCustomer bankCustomerDetails;
}
