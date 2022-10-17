package com.bridgelabz.Bank_Application.service;

import com.bridgelabz.Bank_Application.dto.CustomerBalance;
import com.bridgelabz.Bank_Application.dto.EstatementLists;
import com.bridgelabz.Bank_Application.model.BankCustomer;
import com.bridgelabz.Bank_Application.model.Estatements;

import java.util.List;

public interface IAtmService {
    BankCustomer depositeAmount(Long atmCardNumber, Long atmPin, double depositeAmount);

    BankCustomer withDrawAmount(Long atmCardNumber, Long atmPin, double withDrawingAmount);

    CustomerBalance getAccountBalance(Long atmCardNumber, Long atmPin);

    Long changeAtmPin(Long atmCardNumber, Long currentAtmPin, Long newAtmPin);

    String changePhoneNumber(Long atmCardNumber, Long atmPin, String phoneNumber);

    String BlockAccount(Long atmCardNumber, Long atmPin);

    String BlockAtmCard(Long atmCardNumber, Long atmPin);

    List<Estatements> getEstatementOftheAccount(Long atmCardNumber, Long atmPin);

    List<Estatements> getEstatementOftheAccountOnThatParticularDate(Long atmCardNumber, Long atmPin, String dateOfEstatement);

    List<Estatements> getDepositedEstatementBetweenDates(Long atmCardNumber, Long atmPin, String fromDate, String toDate);

    List<Estatements> getWithDrawnEstatementBetweenDates(Long atmCardNumber, Long atmPin, String fromDate, String toDate);

    List<EstatementLists> getEstatementOfThatDatePeriod(Long atmCardNumber, Long atmPin, String fromDate, String toDate);


}
