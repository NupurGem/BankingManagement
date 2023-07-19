package com.example.BankingManagement.serviceImpl;

import com.example.BankingManagement.model.AdminDetails;
import com.example.BankingManagement.service.AdminService;
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
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    void getBalance() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setAccountNo(2000103);
        adminDetails.setBalance(100.0);
        adminService.create(adminDetails);

        double balance = adminService.getBalance(adminDetails.getAccountNo());
        assertEquals(100,balance);

    }

    @Test
    void depositAmount() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1L);
        adminDetails.setBalance(100.0);

        adminService.create(adminDetails);

        double transactionAmount= 300;
        adminService.depositAmount(adminDetails.getId(),transactionAmount);

        AdminDetails updateAdminDetails= adminService.getCustomerDetails(adminDetails.getId());
        double expectedBalance = adminDetails.getBalance() + transactionAmount;
        assertEquals(expectedBalance, updateAdminDetails.getBalance());
    }

    @Test
    void updateBalance() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1L);
        adminDetails.setBalance(100.0);

        adminService.create(adminDetails);
        double newBalance = 50;
        adminService.updateBalance(adminDetails.getId(),newBalance);
        AdminDetails updatedAdminDetails = adminService.getCustomerDetails(adminDetails.getId());
        assertEquals(newBalance, updatedAdminDetails.getBalance());

    }

    @Test
    void withdrawAmountNormalCheck() {
        AdminDetails adminDetails = new AdminDetails(1L, 100);
        double withdraw = 20L;
        adminService.withdrawAmount(1l, withdraw);
        double actual = adminService.getBalance(200101L);
        double expectedbalance = 1780L;
        assertEquals(actual, expectedbalance);
    }

    @Test
    void withdrawNegativeBalance() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1L);
        adminDetails.setBalance(100.0);

        adminService.create(adminDetails);

        double transactionAmount = -150.0;
        adminService.withdrawAmount(adminDetails.getId(), transactionAmount);
        AdminDetails updatedAdminDetails = adminService.getCustomerDetails(adminDetails.getId());
        assertEquals(250, updatedAdminDetails.getBalance(), 0.01);

   }


    @Test
    void transferAmount() {
        AdminDetails adminDetails = new AdminDetails(1L, 100);
        double transaction = 300L;
        adminService.transferAmount(1l, 2l, transaction);
        double actual = adminService.getBalance(200101L);
        double expectedbalance = 1500;
        assertEquals(actual, expectedbalance);
    }


    @Test
    void getCustomerDetails() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1L);
        adminDetails.setBalance(1000.0);
        adminDetails.setSourceId(2L);

        adminService.create(adminDetails);

        AdminDetails retrievedAdminDetails = adminService.getCustomerDetails(adminDetails.getId());

        assertEquals(adminDetails.getId(), retrievedAdminDetails.getId());
        assertEquals(adminDetails.getBalance(), retrievedAdminDetails.getBalance());
        assertEquals(adminDetails.getSourceId(),retrievedAdminDetails.getSourceId());
    }
}