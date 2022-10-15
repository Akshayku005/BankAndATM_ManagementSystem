package com.bridgelabz.Bank_Application.repository;

import com.bridgelabz.Bank_Application.model.Estatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstatementRepository extends JpaRepository<Estatements, Long> {
    @Query(value = "select * from bankuser.estatement where bank_customer_details_account_no like :accountNumber%", nativeQuery = true)
    List<Estatements> findByBankCustomerDetails(Long accountNumber);
}
