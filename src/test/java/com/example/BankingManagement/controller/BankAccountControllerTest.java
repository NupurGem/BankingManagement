package com.example.BankingManagement.controller;

import com.example.BankingManagement.dto.BankAccountDto;
import com.example.BankingManagement.model.BankAccount;
import com.example.BankingManagement.repository.BankAccountRepository;
import com.example.BankingManagement.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class BankAccountControllerTest {


    @Autowired
    private BankAccountController bankAccountController;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;



    @Test
    void deleteCustomer() {
        long id = 1L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccountRepository.save(bankAccount);

        // Perform the test
        bankAccountService.deleteCustomer(id);

        // Verify the deletion
        Optional<BankAccount> result = bankAccountRepository.findById(id);
        assertEquals(result, Optional.empty());
    }

    @Test
    void getCustomer() {
        long id = 1L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setFirstName("John");
        bankAccount.setLastName("Doe");
        bankAccount.setEmail("john.doe@example.com");
        bankAccount.setContactNo("1234567890");
        bankAccount.setAddress("123 Main St");
        bankAccountRepository.save(bankAccount);

        ResponseEntity<BankAccountDto> response = bankAccountController.getCustomer(id);
       // assertEquals(HttpStatus.OK, response.getStatusCode());

        BankAccountDto result = response.getBody();
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getContactNo());
        assertEquals("123 Main St", result.getAddress());

    }

    @Test
    void createCustomer()
    {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setFirstName("John");
        bankAccount.setLastName("Doe");
        bankAccount.setContactNo("1234567890");
        bankAccount.setAddress("123 Main Street");
        bankAccount.setAccountType("Savings");

        BankAccount createdAccount = bankAccountService.create(bankAccount);

        assertNotNull(createdAccount);
        assertEquals(bankAccount.getFirstName(), createdAccount.getFirstName());
        assertEquals(bankAccount.getLastName(), createdAccount.getLastName());
    }
}