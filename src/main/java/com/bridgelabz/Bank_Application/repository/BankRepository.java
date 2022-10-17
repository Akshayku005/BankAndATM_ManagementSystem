package com.bridgelabz.Bank_Application.repository;

import com.bridgelabz.Bank_Application.model.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankCustomer, Long> {
    @Query(value = "select * from bankuser.customeraccounttable where aadhar_number like :aadharNumber%", nativeQuery = true)
    List<BankCustomer> findByAadharNumber(String aadharNumber);
    @Query(value = "select * from bankuser.customeraccounttable where account_holder_name like :name%", nativeQuery = true)
    List<BankCustomer> findByName(String name);
    @Query(value = "select * from bankuser.customeraccounttable where email like :email%", nativeQuery = true)
    List<BankCustomer> findByEmail(String email);
    @Query(value = "select * from bankuser.customeraccounttable where phone_no like :phoneNumber% ", nativeQuery = true)
    List<BankCustomer> findByPhoneNumber(String phoneNumber);
    @Query(value = "select * from bankuser.customeraccounttable where  account_holder_name like :name% and phone_no = :phoneNumber", nativeQuery = true)
    List<BankCustomer> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<BankCustomer> findByAtmCardNumberAndAtmPin(Long atmCardNumber, Long atmPin);

    Optional<BankCustomer> findByAtmCardNumber(Long atmCardNumber);

    Optional<BankCustomer> findByAtmPin(Long atmPin);
}
