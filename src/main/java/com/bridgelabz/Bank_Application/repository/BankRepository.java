package com.bridgelabz.Bank_Application.repository;

import com.bridgelabz.Bank_Application.model.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankCustomer, Long> {

}
