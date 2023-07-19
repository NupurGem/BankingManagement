package com.example.BankingManagement.repository;

import com.example.BankingManagement.model.AdminDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AdminRepository extends JpaRepository<AdminDetails, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AdminDetails set balance =balance+?2 where id=?1")
    void saveTransactionAmountByID(long id, double transactionAmount);

    @Query("select balance from AdminDetails where account_no = ?1")
   Double getAccountByNumber(long accountNo);




}
