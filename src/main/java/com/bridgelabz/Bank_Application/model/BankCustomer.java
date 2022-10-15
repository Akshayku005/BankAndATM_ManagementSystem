package com.bridgelabz.Bank_Application.model;

import com.bridgelabz.Bank_Application.dto.BankCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Customeraccounttable")
public class BankCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountNo;
    private String accountHolderName;
    private Long atmPin;
    private String address;
    private String phoneNo;
    private String email;
    private String aadharNumber;
    private String dob;
    private LocalDateTime accountCreatedDate=LocalDateTime.now();
    private LocalDateTime accountUpdatedDate;
    private Boolean accountIsActive;
    private double totalBalance;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Estatements> estatementsList;

    public BankCustomer(BankCustomerDto bankCustomerDto) {
        this.accountHolderName=bankCustomerDto.getAccountHolderName();
        this.address=bankCustomerDto.getAddress();
        this.phoneNo=bankCustomerDto.getPhoneNo();
        this.email=bankCustomerDto.getEmail();
        this.aadharNumber=bankCustomerDto.getAadharNumber();
        this.dob=bankCustomerDto.getDob();

    }
}
