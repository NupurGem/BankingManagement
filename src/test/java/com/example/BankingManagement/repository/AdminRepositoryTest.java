package com.example.BankingManagement.repository;

import com.example.BankingManagement.model.AdminDetails;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;


    @Test
    void saveTransactionAmountByID() {
        long id = 1L;
        double transactionAmount = 100.0;

        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(id);
        adminDetails.setBalance(500.0);
        adminRepository.save(adminDetails);

        adminRepository.saveTransactionAmountByID(id, transactionAmount);
        AdminDetails updatedAdminDetails = adminRepository.findById(id).orElse(null);

    }

    @Test
    void getAccountByNumber() {
        long accountNo = 123456789L;
        double balance = 500.0;

        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setAccountNo(accountNo);
        adminDetails.setBalance(balance);
        adminRepository.save(adminDetails);

        Double result = adminRepository.getAccountByNumber(accountNo);
        assertEquals(balance, result);

    }
}