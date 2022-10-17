package com.bridgelabz.Bank_Application.controller;

import com.bridgelabz.Bank_Application.dto.CustomerBalance;
import com.bridgelabz.Bank_Application.dto.EstatementLists;
import com.bridgelabz.Bank_Application.dto.ResponseDTO;
import com.bridgelabz.Bank_Application.model.BankCustomer;
import com.bridgelabz.Bank_Application.model.Estatements;
import com.bridgelabz.Bank_Application.service.IAtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atm")
public class ATMController {
    @Autowired
    IAtmService atmService;

    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @param depositeAmount
     * @purpose Deposite money to customer account by using there atmcard nunmber and atm pin
     */
    @PutMapping("/depositeamountbyatm")
    public ResponseEntity<ResponseDTO> depositeMoney(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader double depositeAmount) {
        BankCustomer bankCustomer = atmService.depositeAmount(atmCardNumber, atmPin,depositeAmount);
        ResponseDTO response = new ResponseDTO("Amount deposited successfully ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @param depositeAmount
     * @purpose withDraw money to customer account by using there atmcard nunmber and atm pin
     */
    @PutMapping("/withdrawmoneybyatm")
    public ResponseEntity<ResponseDTO> withDrawMoney(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader double depositeAmount) {
        BankCustomer bankCustomer = atmService.withDrawAmount(atmCardNumber, atmPin,depositeAmount);
        ResponseDTO response = new ResponseDTO("Amount withdrawn successfully ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @purpose get customer account balance in atm machine using atmcard nunmber and atm pin
     */
    @GetMapping("/getaccountbalancebyatm")
    public ResponseEntity<ResponseDTO> getAccountBalance(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin) {
        CustomerBalance balance = atmService.getAccountBalance(atmCardNumber, atmPin);
        ResponseDTO response = new ResponseDTO("your account balance is  ", balance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param atmCardNumber
     * @param oldAtmPin
     * @param newAtmPin
     * @purpose to change customer atm pin number
     */
    @PutMapping("/changeatmpin")
    public ResponseEntity<ResponseDTO> changeAtmPin(@RequestHeader Long atmCardNumber, @RequestHeader Long oldAtmPin, @RequestHeader Long newAtmPin) {
        Long bankCustomer = atmService.changeAtmPin(atmCardNumber, oldAtmPin,newAtmPin);
        ResponseDTO response = new ResponseDTO("ATM Pin changed successfully your new ATM pin is ", bankCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     *
     * @param atmCardNumber
     * @param oldAtmPin
     * @param newAtmPin
     * @purpose to change customer phone number
     */
    @PutMapping("/changephonenumber")
    public ResponseEntity<String> changePhoneNumber(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader String phoneNumber) {
        String bankCustomer = atmService.changePhoneNumber(atmCardNumber, atmPin,phoneNumber);
        return new ResponseEntity<>(bankCustomer, HttpStatus.OK);
    }

    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @purpose to block customer account temporarly
     */

    @PutMapping("/blockaccounttemporarly")
    public ResponseEntity<String> blockAccountTemporarly(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin) {
        String bankCustomer = atmService.BlockAccount(atmCardNumber, atmPin);
        return new ResponseEntity<>(bankCustomer, HttpStatus.OK);
    }
    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @purpose to block customer atm card temporarly
     */
    @PutMapping("/blockatmcardtemporarly")
    public ResponseEntity<String> blockAtmCardTemporarly(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin) {
        String bankCustomer = atmService.BlockAtmCard(atmCardNumber, atmPin);
        return new ResponseEntity<>(bankCustomer, HttpStatus.OK);
    }

    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @purpose get estatement till now
     */
    @GetMapping("/getestatementatm")
    public ResponseEntity<ResponseDTO> getEstatementByAccountNumber(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin) {
        List<Estatements>  estatements= atmService.getEstatementOftheAccount(atmCardNumber,atmPin);
        ResponseDTO response = new ResponseDTO("this your estatement  ", estatements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getestatementbydateatm")
    public ResponseEntity<ResponseDTO> getEstatementByAccountNumberAndDate(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader String dateOfEstatement) {
        List<Estatements> estatements= atmService.getEstatementOftheAccountOnThatParticularDate(atmCardNumber,atmPin, dateOfEstatement);
        ResponseDTO response = new ResponseDTO("this your estatement of that date  ", estatements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @param fromDate
     * @param toDate
     * @purpose get deposited E-statement  to that given dates
     */
    @GetMapping("/getdepositedestatementbydateatm")
    public ResponseEntity<ResponseDTO> getEstatementOfDepositedAmountByAccountNumberAndDates(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader String fromDate, @RequestHeader String toDate) {
        List<Estatements>  estatements= atmService.getDepositedEstatementBetweenDates(atmCardNumber,atmPin, fromDate, toDate);
        ResponseDTO response = new ResponseDTO("this your money added estatement of the given dates  ", estatements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @param fromDate
     * @param toDate
     * @purpose get withdrawn E-statement  to that given dates
     */
    @GetMapping("/getwithdrawnstatementbydateatm")
    public ResponseEntity<ResponseDTO> getEstatementOfWithdrawnAmountByAccountNumberAndDates(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader String fromDate, @RequestHeader String toDate) {
        List<Estatements>  estatements= atmService.getWithDrawnEstatementBetweenDates(atmCardNumber,atmPin, fromDate, toDate);
        ResponseDTO response = new ResponseDTO("this your money withdrawn estatement of the given dates  ", estatements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param atmCardNumber
     * @param atmPin
     * @param fromDate
     * @param toDate
     * @purpose to get deposited and withdrawn tranction statement with the given dates
     */
    @GetMapping("/getestatementofthatperiodatm")
    public ResponseEntity<ResponseDTO> getEstatementOfThatPeriod(@RequestHeader Long atmCardNumber, @RequestHeader Long atmPin, @RequestHeader String fromDate, @RequestHeader String toDate) {
        List<EstatementLists>  estatements= atmService.getEstatementOfThatDatePeriod(atmCardNumber,atmPin, fromDate, toDate);
        ResponseDTO response = new ResponseDTO("this is your estatement of the given dates  ", estatements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
