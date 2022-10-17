package com.bridgelabz.Bank_Application.service;

import com.bridgelabz.Bank_Application.dto.CustomerBalance;
import com.bridgelabz.Bank_Application.dto.EstatementLists;
import com.bridgelabz.Bank_Application.exception.ATMException;
import com.bridgelabz.Bank_Application.model.BankCustomer;
import com.bridgelabz.Bank_Application.model.Estatements;
import com.bridgelabz.Bank_Application.repository.BankRepository;
import com.bridgelabz.Bank_Application.repository.EstatementRepository;
import com.bridgelabz.Bank_Application.util.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtmService implements IAtmService {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    EstatementRepository estatementRepository;
    @Autowired
    EmailSenderService emailSender;

    @Override
    public BankCustomer depositeAmount(Long atmCardNumber, Long atmPin, double depositeAmount) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                double totalBalance = customer.get().getTotalBalance() + depositeAmount;
                customer.get().setTotalBalance(customer.get().getTotalBalance() + depositeAmount);
                System.out.println("total amount(" + customer.get().getTotalBalance() + ") + deposite amount(" + depositeAmount + ")  =" + totalBalance);
                List<Estatements> estatementsList = new ArrayList<>();
                Estatements estatements = new Estatements();
                estatements.setDateOfDeposite(LocalDateTime.now());
                estatements.setMoneyDeposited(depositeAmount);
                estatements.setMoneyWithdrawn(0);
                estatements.setBankCustomerDetails(customer.get());
                estatements.setTotalAmount(customer.get().getTotalBalance());
                estatementsList.add(estatements);
                customer.get().setEstatementsList(estatementsList);
                bankRepository.save(customer.get());
                emailSender.send(customer.get().getEmail(), "Account Created successfully",
                        "Hi " + customer.get().getAccountHolderName() + "," +
                                " Amount of " + depositeAmount + " Rs as been deposited to your account. Now your total balance is " + totalBalance + " Rs");
                return customer.get();
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        }
        throw new ATMException(HttpStatus.FOUND, "your ATM pin or cardnumber is incorrect");
    }

    @Override
    public BankCustomer withDrawAmount(Long atmCardNumber, Long atmPin, double withDrawingAmount) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                if (customer.get().getTotalBalance() >= withDrawingAmount) {
                    double totalBalance = customer.get().getTotalBalance() - withDrawingAmount;
                    customer.get().setTotalBalance(customer.get().getTotalBalance() - withDrawingAmount);
                    System.out.println("total amount(" + customer.get().getTotalBalance() + ") - withDraw amount(" + withDrawingAmount + ")  =" + totalBalance);
                    List<Estatements> estatementsList = new ArrayList<>();
                    Estatements estatements = new Estatements();
                    estatements.setDateOfWithDraw(LocalDateTime.now());
                    estatements.setMoneyWithdrawn(withDrawingAmount);
                    estatements.setBankCustomerDetails(customer.get());
                    estatements.setTotalAmount(customer.get().getTotalBalance());
                    estatementsList.add(estatements);
                    customer.get().setEstatementsList(estatementsList);
                    bankRepository.save(customer.get());
                    emailSender.send(customer.get().getEmail(), "Account Created successfully",
                            "Hi " + customer.get().getAccountHolderName() + "," +
                                    " Amount of " + withDrawingAmount + " Rs as been withdraw from your account . Now your total balance is " + totalBalance + " Rs");
                    return customer.get();
                }
                throw new ATMException(HttpStatus.FOUND, "your withdrawing amount is more then your account balance, please check your account balance");
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        }
        throw new ATMException(HttpStatus.FOUND, "your ATM pin or cardnumber is incorrect");
    }

    @Override
    public CustomerBalance getAccountBalance(Long atmCardNumber, Long atmPin) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                CustomerBalance customerBalance = new CustomerBalance();
                customerBalance.setAccountNumber(customer.get().getAccountNo());
                customerBalance.setTotalBalance(customer.get().getTotalBalance());
                return customerBalance;
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public Long changeAtmPin(Long atmCardNumber, Long currentAtmPin, Long newAtmPin) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, currentAtmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                customer.get().setAtmPin(newAtmPin);
                bankRepository.save(customer.get());
                return newAtmPin;
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public String changePhoneNumber(Long atmCardNumber, Long atmPin, String phoneNumber) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                customer.get().setPhoneNo(phoneNumber);
                bankRepository.save(customer.get());
                return "Phone number as changed";
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public String BlockAccount(Long atmCardNumber, Long atmPin) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                customer.get().setAccountIsActive(false);
                bankRepository.save(customer.get());
                return "Account Temporarly blocked successfully";
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public String BlockAtmCard(Long atmCardNumber, Long atmPin) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                customer.get().setIsAtmCardActive(false);
                bankRepository.save(customer.get());
                return "your atm card Temporarly blocked successfully";
            }
            throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getEstatementOftheAccount(Long atmCardNumber, Long atmPin) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                List<Estatements> estatements = estatementRepository.findcustomerAllTransactions(customer.get().getAccountNo());
                if (estatements.equals(null)) {
                    throw new ATMException(HttpStatus.FOUND, "there are no transaction yet");
                } else {
                    return estatements;
                }
            } else throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getEstatementOftheAccountOnThatParticularDate(Long atmCardNumber, Long atmPin, String dateOfEstatement) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                List<Estatements> estatements = estatementRepository.findByBankCustomerDetailsWithDate(customer.get().getAccountNo(), dateOfEstatement);
                if (estatements.equals(null)) {
                    throw new ATMException(HttpStatus.FOUND, "there are no transaction yet");
                } else {
                    return estatements;
                }
            } else throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getDepositedEstatementBetweenDates(Long atmCardNumber, Long atmPin, String fromDate, String toDate) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                List<Estatements> estatements = estatementRepository.findByCustomerDepositedEstatementWithFromDateAndToDate(customer.get().getAccountNo(), fromDate, toDate);
                if (estatements.equals(null)) {
                    throw new ATMException(HttpStatus.FOUND, "there are no transaction yet");
                } else {
                    return estatements;
                }
            } else throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getWithDrawnEstatementBetweenDates(Long atmCardNumber, Long atmPin, String fromDate, String toDate) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                List<Estatements> estatements = estatementRepository.findByCustomerWithDrawnEstatementWithFromDateAndToDate(customer.get().getAccountNo(), fromDate, toDate);
                if (estatements.equals(null)) {
                    throw new ATMException(HttpStatus.FOUND, "there are no transaction yet");
                } else {
                    return estatements;
                }
            } else throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<EstatementLists> getEstatementOfThatDatePeriod(Long atmCardNumber, Long atmPin, String fromDate, String toDate) {
        Optional<BankCustomer> customer = bankRepository.findByAtmCardNumberAndAtmPin(atmCardNumber, atmPin);
        if (customer.isPresent()) {
            if (customer.get().getIsAtmCardActive().equals(true)) {
                List<EstatementLists> estatements = new ArrayList<>();
                EstatementLists estate = new EstatementLists();
                estate.setDepositedEstatement(getDepositedEstatementBetweenDates(atmCardNumber, atmPin, fromDate, toDate));
                estate.setWithDrawnListEstatement(getWithDrawnEstatementBetweenDates(atmCardNumber, atmPin, fromDate, toDate));
                estatements.add(estate);
                return estatements;
            } else throw new ATMException(HttpStatus.FOUND, "your Atm card number is not activated ");
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }
}