package com.bridgelabz.Bank_Application.service;

import com.bridgelabz.Bank_Application.dto.BankCustomerDto;
import com.bridgelabz.Bank_Application.dto.CustomerBalance;
import com.bridgelabz.Bank_Application.dto.EstatementLists;
import com.bridgelabz.Bank_Application.dto.ResponseDTO;
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
public class BankService implements IBankService {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    EstatementRepository estatementRepository;
    @Autowired
    EmailSenderService emailSender;


    @Override
    public BankCustomer addCustomer(BankCustomerDto bankCustomerDto) {
        BankCustomer newCustomer = new BankCustomer(bankCustomerDto);
        BankService pin = new BankService();
        newCustomer.setAtmPin(pin.generateATMPin());
        newCustomer.setAtmCardNumber(pin.generateATMCardNumber());
        newCustomer.setIsAtmCardActive(true);
        newCustomer.setAccountIsActive(true);
        bankRepository.save(newCustomer);
        emailSender.send(newCustomer.getEmail(), "Account Created successfully",
                "Hi " + newCustomer.getAccountHolderName() + "," +
                        " Your Account in our bank created successfully Below are the details of your account " +
                        "Account.no : " + newCustomer.getAccountNo() + " " +
                        "Cartpin : " + newCustomer.getAtmPin());
        return newCustomer;
    }

    @Override
    public Optional<BankCustomer> updateCustomers(BankCustomerDto bankCustomerDto, Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            customer.get().setAccountHolderName(bankCustomerDto.getAccountHolderName());
            customer.get().setAddress(bankCustomerDto.getAddress());
            customer.get().setDob(bankCustomerDto.getDob());
            customer.get().setEmail(bankCustomerDto.getEmail());
            customer.get().setAadharNumber(bankCustomerDto.getAadharNumber());
            customer.get().setPhoneNo(bankCustomerDto.getPhoneNo());
            customer.get().setAccountUpdatedDate(LocalDateTime.now());
            bankRepository.save(customer.get());
            return customer;
        } else
            throw new ATMException(HttpStatus.FOUND, "Customer account number incorrect!!");
    }

    @Override
    public List<BankCustomer> getAllCustomer() {
        List<BankCustomer> customers = bankRepository.findAll();
        if (customers.equals(null)) {
            throw new ATMException(HttpStatus.FOUND, "No Customers found");
        } else {
            return customers;
        }
    }

    @Override
    public Optional<BankCustomer> getCustomerByAccountNo(Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            return customer;
        } else {
            throw new ATMException(HttpStatus.FOUND, "Account not found");
        }
    }

    @Override
    public BankCustomer depositeAmount(double depositeAmount, Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
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
        } else {
            throw new ATMException(HttpStatus.FOUND, "Account not found");
        }
    }

    @Override
    public BankCustomer withDrawAmount(double withDrawAmount, Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            if (customer.get().getTotalBalance() >= withDrawAmount) {
                double totalBalance = customer.get().getTotalBalance() - withDrawAmount;
                customer.get().setTotalBalance(customer.get().getTotalBalance() - withDrawAmount);
                System.out.println("total amount(" + customer.get().getTotalBalance() + ") - withDraw amount(" + withDrawAmount + ")  =" + totalBalance);
                List<Estatements> estatementsList = new ArrayList<>();
                Estatements estatements = new Estatements();
                estatements.setDateOfWithDraw(LocalDateTime.now());
                estatements.setMoneyWithdrawn(withDrawAmount);
                estatements.setBankCustomerDetails(customer.get());
                estatements.setTotalAmount(customer.get().getTotalBalance());
                estatementsList.add(estatements);
                customer.get().setEstatementsList(estatementsList);
                bankRepository.save(customer.get());
                emailSender.send(customer.get().getEmail(), "Account Created successfully",
                        "Hi " + customer.get().getAccountHolderName() + "," +
                                " Amount of " + withDrawAmount + " Rs as been withdraw from your account . Now your total balance is " + totalBalance + " Rs");
                return customer.get();
            } else {
                throw new ATMException(HttpStatus.FOUND, "withdraw amount is more themn the total amount please check your balance");
            }
        } else {
            throw new ATMException(HttpStatus.FOUND, "Account not found");
        }
    }

    @Override
    public CustomerBalance getAccountBalance(Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            CustomerBalance customerBalance = new CustomerBalance();
            customerBalance.setAccountNumber(customer.get().getAccountNo());
            customerBalance.setTotalBalance(customer.get().getTotalBalance());
            return customerBalance;
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getEstatementOftheAccount(Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            List<Estatements> estatements = estatementRepository.findcustomerAllTransactions(accountNumber);
            return estatements;
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getEstatementOftheAccountOnThatParticularDate(Long accountNumber, String dateOfEstatement) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            List<Estatements> estatements = estatementRepository.findByBankCustomerDetailsWithDate(accountNumber, dateOfEstatement);
            return estatements;
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getDepositedEstatementBetweenDates(Long accountNumber, String fromDate, String toDate) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            List<Estatements> estatements = estatementRepository.findByCustomerDepositedEstatementWithFromDateAndToDate(accountNumber, fromDate, toDate);
            return estatements;
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<Estatements> getWithDrawnEstatementBetweenDates(Long accountNumber, String fromDate, String toDate) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            List<Estatements> estatements = estatementRepository.findByCustomerWithDrawnEstatementWithFromDateAndToDate(accountNumber, fromDate, toDate);
            return estatements;
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<EstatementLists> getEstatementOfThatDatePeriod(Long accountNumber, String fromDate, String toDate) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            List<EstatementLists> estatements = new ArrayList<>();
            EstatementLists estate = new EstatementLists();
            estate.setDepositedEstatement(getDepositedEstatementBetweenDates(accountNumber, fromDate, toDate));
            estate.setWithDrawnListEstatement(getWithDrawnEstatementBetweenDates(accountNumber, fromDate, toDate));
            estatements.add(estate);
            return estatements;
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }


    @Override
    public ResponseDTO deleteCustomerAccount(Long accountNumber) {
        Optional<BankCustomer> customer = bankRepository.findById(accountNumber);
        if (customer.isPresent()) {
            bankRepository.deleteById(accountNumber);
            return new ResponseDTO("Account delected successfully",customer);
        } else throw new ATMException(HttpStatus.FOUND, "Account not found");
    }

    @Override
    public List<BankCustomer> getCustomerAccountDetailsByAadharNumber(String aadharNumber) {
        List<BankCustomer>customer=bankRepository.findByAadharNumber(aadharNumber);
        if (customer.equals(null)) {
            throw new ATMException(HttpStatus.FOUND,"With aadharNumber, customer not found");
        }else{
           return customer;
        }
    }

    @Override
    public List<BankCustomer> getCustomerAccountDetailsByName(String name) {
        List<BankCustomer>customer=bankRepository.findByName(name);
        if (customer.equals(null)) {
            throw new ATMException(HttpStatus.FOUND,"With customer name no details found");
        }else{
            return customer;
        }
    }

    @Override
    public List<BankCustomer> getCustomerAccountDetailsByEmail(String email) {
        List<BankCustomer>customer=bankRepository.findByEmail(email);
        if (customer.equals(null)) {
            throw new ATMException(HttpStatus.FOUND,"With customer name no details found");
        }else{
            return customer;
        }
    }

    @Override
    public List<BankCustomer> getCustomerAccountDetailsByPhoneNumber(String phoneNumber) {
        List<BankCustomer>customer=bankRepository.findByPhoneNumber(phoneNumber);
        if (customer.equals(null)) {
            throw new ATMException(HttpStatus.FOUND,"With customer phone number no details found");
        }else{
            return customer;
        }
    }

    @Override
    public List<BankCustomer> getCustomerAccountDetailsByNameAndPhonenNumber(String name, String phoneNumber) {
        List<BankCustomer>customer=bankRepository.findByNameAndPhoneNumber(name,phoneNumber);
        if (customer.equals(null)) {
            throw new ATMException(HttpStatus.FOUND,"With customer name and phone number, no details found");
        }else{
            return customer;
        }
    }

    public Long generateATMPin() {
        int min = 1000;
        int max = 8888;
        Long b = Long.valueOf((int) (Math.random() * (max - min + 1) + min));
        return b;
    }
    public Long generateATMCardNumber() {
        Long min = 100000000000l;
        Long max = 888888888888l;
        Long b = (long)(Math.random() * (max - min + 1) + min);
        return b;
    }
}
