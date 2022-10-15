package com.bridgelabz.Bank_Application.controller;

import com.bridgelabz.Bank_Application.dto.BankCustomerDto;
import com.bridgelabz.Bank_Application.dto.CustomerBalance;
import com.bridgelabz.Bank_Application.dto.ResponseDTO;
import com.bridgelabz.Bank_Application.model.BankCustomer;
import com.bridgelabz.Bank_Application.model.Estatements;
import com.bridgelabz.Bank_Application.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired
    IBankService bankService;
    @PostMapping("/addnewcustomeraccount")
    public ResponseEntity<ResponseDTO> addCustomerAccount(@Valid @RequestBody BankCustomerDto bankCustomerDto) {
        BankCustomer bankCustomer = bankService.addCustomer(bankCustomerDto);
        ResponseDTO response = new ResponseDTO("Account created successfully ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updatenote")
    public ResponseEntity<ResponseDTO> updateCustomerAccount(@Valid@RequestBody BankCustomerDto bankCustomerDto,@RequestHeader Long accountNumber) {
        Optional<BankCustomer> bankCustomer = bankService.updateCustomers(bankCustomerDto,accountNumber);
        ResponseDTO response = new ResponseDTO("Account updated successfully ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getallcustomer")
    public ResponseEntity<ResponseDTO> getAllcustomers() {
        List<BankCustomer> bankCustomer = bankService.getAllCustomer();
        ResponseDTO response = new ResponseDTO("This are the customers ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getcustomer")
    public ResponseEntity<ResponseDTO> getAllcustomerByAccountno(@RequestHeader Long accountNumber) {
        Optional<BankCustomer> bankCustomer = bankService.getCustomerByAccountNo(accountNumber);
        ResponseDTO response = new ResponseDTO("This is the customer with this account number ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/depositeamount")
    public ResponseEntity<ResponseDTO> depositeMoney(@RequestHeader Long accountNumber, @RequestHeader double depositeAmount) {
        BankCustomer bankCustomer = bankService.depositeAmount(depositeAmount,accountNumber);
        ResponseDTO response = new ResponseDTO("Amount deposited successfully ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/withdrawmoney")
    public ResponseEntity<ResponseDTO> withDrawMoney(@RequestHeader Long accountNumber, @RequestHeader double withdrawAmount) {
        BankCustomer bankCustomer = bankService.withDrawAmount(withdrawAmount,accountNumber);
        ResponseDTO response = new ResponseDTO("Amount withdrawn successfully ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getaccountbalance")
    public ResponseEntity<ResponseDTO> getAccountBalance(@RequestHeader Long accountNumber) {
        CustomerBalance balance = bankService.getAccountBalance(accountNumber);
        ResponseDTO response = new ResponseDTO("your account balance is  ", balance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getestatement")
    public ResponseEntity<ResponseDTO> getEstatementByAccountNumber(@RequestHeader Long accountNumber) {
        List<Estatements>  estatements= bankService.getEstatementOftheAccount(accountNumber);
        ResponseDTO response = new ResponseDTO("this your estatement  ", estatements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
