package com.example.BankingManagement.repository;

import com.example.BankingManagement.dto.BankAccountDto;
import com.example.BankingManagement.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
   //@Query("SELECT b.firstName AS firstName, b.lastName AS lastName,b.email AS email, b.contactNo AS contactNo, b.address AS address FROM BankAccount b WHERE b.id = ?1")
@Query("SELECT new com.example.BankingManagement.dto.BankAccountDto(b.firstName, b.lastName,b.email, b.contactNo, b.address) FROM BankAccount b WHERE b.id = ?1")
BankAccountDto findProjectedById(long id);

}
