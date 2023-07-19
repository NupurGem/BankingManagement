package com.example.BankingManagement.controller;

import com.example.BankingManagement.exception.ResourceNotFoundException;
import com.example.BankingManagement.model.AdminDetails;
import com.example.BankingManagement.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class AdminControllerTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminController adminController;


    @BeforeEach
    void setup() {
        adminRepository.deleteAll();
    }


    @Test
    void getCustomerDetails() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1L);
        adminDetails.setAccountNo(200101);
        adminDetails.setBalance(1000);

        adminRepository.save(adminDetails);

        AdminDetails result = adminController.getCustomerDetails(1L);

        assertNotNull(result);                         // verify
        assertEquals(adminDetails.getId(), result.getId());
        assertEquals(adminDetails.getAccountNo(), result.getAccountNo());
        assertEquals(adminDetails.getBankAccount(), result.getBankAccount());

    }

    @Test
    void getCustomerDetails_NotFound(){
        long nonExistentCustomerId = 1L;

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> adminController.getCustomerDetails(nonExistentCustomerId));
        assertEquals("AdminDetails not found with id : '1' ",exception.getMessage());
    }


    @Test
    void deleteAccount() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1);
        adminDetails.setAccountNo(123456789);
        adminDetails.setBalance(1000);

        adminRepository.save(adminDetails);
        adminController.deleteAccount(adminDetails.getId());
        assertFalse(adminRepository.existsById(adminDetails.getId()));
    }


    @Test
    void depositAmount() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1);
        adminDetails.setAccountNo(123456789);
        adminDetails.setBalance(1000);

        adminRepository.save(adminDetails);

        adminController.depositAmount(adminDetails.getId(), 500);

        AdminDetails updatedAdminDetails = adminRepository.findById(adminDetails.getId()).orElse(null);

        assertNotNull(updatedAdminDetails);
        assertEquals(1500, updatedAdminDetails.getBalance());
    }

    @Test
    void withdrawAmount() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1);
        adminDetails.setAccountNo(123456789);
        adminDetails.setBalance(1000);
        adminRepository.save(adminDetails);

        adminController.withdrawAmount(adminDetails.getId(), 500);

        AdminDetails updatedAdminDetails = adminRepository.findById(adminDetails.getId()).orElse(null);   // fetching the object data through repo
        assertNotNull(updatedAdminDetails);
        assertEquals(500, updatedAdminDetails.getBalance());

    }

    @Test
    void transferAmount() {
        AdminDetails sourceAccount = new AdminDetails();
        sourceAccount.setId(1);
        sourceAccount.setAccountNo(123456789);
        sourceAccount.setBalance(1000);

        AdminDetails targetAccount = new AdminDetails();
        targetAccount.setId(2);
        targetAccount.setAccountNo(987654321);
        targetAccount.setBalance(500);
        adminRepository.save(sourceAccount);
        adminRepository.save(targetAccount);

        adminController.transferAmount(sourceAccount.getId(), targetAccount.getId(), 500);

        AdminDetails updatedSourceAccount = adminRepository.findById(sourceAccount.getId()).orElse(null);
        AdminDetails updatedTargetAccount = adminRepository.findById(targetAccount.getId()).orElse(null);

        assertNotNull(updatedSourceAccount);
        assertNotNull(updatedTargetAccount);
        assertEquals(500, updatedSourceAccount.getBalance());
        assertEquals(1000, updatedTargetAccount.getBalance());

    }

    @Test
    void getBalance() {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setId(1);
        adminDetails.setAccountNo(200101);
        adminDetails.setBalance(1000);

        adminRepository.save(adminDetails);
        double balance = adminController.getBalance(adminDetails.getAccountNo());
        assertEquals(1000, balance);
    }
}