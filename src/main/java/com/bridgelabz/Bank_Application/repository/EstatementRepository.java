package com.bridgelabz.Bank_Application.repository;

import com.bridgelabz.Bank_Application.model.Estatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstatementRepository extends JpaRepository<Estatements, Long> {
    @Query(value = "select * from bankuser.estatement where bank_customer_details_account_no like :accountNumber%", nativeQuery = true)
    List<Estatements> findByBankCustomerDetails(Long accountNumber);
    @Query(value = "select * from bankuser.estatement where bank_customer_details_account_no = :accountNumber or (date_of_deposite = :dateOfEstatement or date_of_with_draw = :dateOfEstatement)", nativeQuery = true)
    List<Estatements> findByBankCustomerDetailsWithDate(Long accountNumber, String dateOfEstatement);
    @Query(value = "select * from bankuser.estatement where bank_customer_details_account_no = :accountNumber and date_of_deposite >= :fromDate and date_of_deposite <= :toDate", nativeQuery = true)
    List<Estatements> findByCustomerDepositedEstatementWithFromDateAndToDate(Long accountNumber, String fromDate, String toDate);
    @Query(value = "select * from bankuser.estatement where bank_customer_details_account_no = :accountNumber and date_of_with_draw >= :fromDate and date_of_with_draw <= :toDate", nativeQuery = true)
    List<Estatements> findByCustomerWithDrawnEstatementWithFromDateAndToDate(Long accountNumber, String fromDate, String toDate);
}
//select * from bankuser.estatement where bank_customer_details_account_no = 2
//        or (date_of_deposite =2022-10-15 and date_of_with_draw = 2022-10-15);

// SELECT * FROM bankuser.estatement WHERE bank_customer_details_account_no = 2 or date_of_deposite
//         and  date_of_with_draw BETWEEN'2022-10-15' and '2022-10-15 ';