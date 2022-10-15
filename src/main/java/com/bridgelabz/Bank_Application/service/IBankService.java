package com.bridgelabz.Bank_Application.service;

import com.bridgelabz.Bank_Application.dto.BankCustomerDto;
import com.bridgelabz.Bank_Application.dto.CustomerBalance;
import com.bridgelabz.Bank_Application.dto.ResponseDTO;
import com.bridgelabz.Bank_Application.model.BankCustomer;
import com.bridgelabz.Bank_Application.model.Estatements;

import java.util.List;
import java.util.Optional;

public interface IBankService {
    BankCustomer addCustomer(BankCustomerDto bankCustomerDto);

    Optional<BankCustomer> updateCustomers(BankCustomerDto bankCustomerDto, Long accountNumber);

    List<BankCustomer> getAllCustomer();

    Optional<BankCustomer> getCustomerByAccountNo(Long accountNumber);

    BankCustomer depositeAmount(double depositeAmount, Long accountNumber);

    BankCustomer withDrawAmount(double withDrawAmount, Long accountNumber);
    CustomerBalance getAccountBalance(Long accountNumber);

    List<Estatements> getEstatementOftheAccount(Long accountNumber);

    ResponseDTO deleteCustomerAccount(Long accountNumber);

    List<BankCustomer> getCustomerAccountDetailsByAadharNumber(String aadharNumber);

    List<BankCustomer> getCustomerAccountDetailsByName(String name);

    List<BankCustomer> getCustomerAccountDetailsByEmail(String email);

    List<BankCustomer> getCustomerAccountDetailsByPhoneNumber(String phoneNumber);

    List<BankCustomer> getCustomerAccountDetailsByNameAndPhonenNumber(String name, String phoneNumber);
}
