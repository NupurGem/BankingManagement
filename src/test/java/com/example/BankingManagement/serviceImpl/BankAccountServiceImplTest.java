package com.example.BankingManagement.serviceImpl;

import com.example.BankingManagement.dto.BankAccountDto;
import com.example.BankingManagement.model.BankAccount;
import com.example.BankingManagement.repository.BankAccountRepository;
import com.example.BankingManagement.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class BankAccountServiceImplTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

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

        BankAccountDto result = bankAccountService.getCustomer(id);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getContactNo());
        assertEquals("123 Main St", result.getAddress());

    }


    @Test
    void deleteCustomer() {
        long id = 1L;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccountRepository.save(bankAccount);

        bankAccountService.deleteCustomer(id);

        Optional<BankAccount> result = bankAccountRepository.findById(id);
        assertEquals(result, Optional.empty());
    }

    @Test
    void updateUser() {
        long userId = 1L;
        String updatedFirstName = "John";
        String updatedLastName = "Doe";
        String updatedEmail = "john@gmail.com";
        String updatedContactNo = "9878754352";
        String updatedPin = "110098";
        long updatedAadhaarNo = Long.parseLong("72159743333");
        String updatedPanNo ="PSE7687970";
        String updatedAccountType ="Savings";
        String updatedAddress ="Delhi";
        LocalDate updatedDob= LocalDate.parse("1999-05-08");


        BankAccount user = new BankAccount();
        user.setId(userId);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("john@gmail.com");
        user.setContactNo("9878754352");
        user.setPin("110098");
        user.setAadhaarNo(Long.parseLong("72159743333"));
        user.setPanNo("PSE7687970");
        user.setAccountType("Savings");
        user.setAddress("Delhi");
        user.setDob(LocalDate.parse("1999-05-08"));

        bankAccountRepository.save(user);

        user.setFirstName(updatedFirstName);
        user.setLastName(updatedLastName);
        user.setEmail(updatedEmail);
        user.setContactNo(updatedContactNo);
        user.setPin(updatedPin);
        user.setAadhaarNo(updatedAadhaarNo);
        user.setPanNo(updatedPanNo);
        user.setAccountType(updatedAccountType);
        user.setAddress(updatedAddress);
        user.setDob(updatedDob);
        BankAccount updatedUser = bankAccountService.updateUser(user);

        BankAccount retrievedUser = bankAccountRepository.findById(userId).orElse(null);

        assertNotNull(updatedUser);
        assertEquals(updatedFirstName, updatedUser.getFirstName());
        assertEquals(updatedLastName, updatedUser.getLastName());
        assertEquals(updatedEmail, updatedUser.getEmail());
        assertEquals(updatedContactNo, updatedUser.getContactNo());
        assertEquals(updatedPin, updatedUser.getPin());
        assertEquals(updatedAadhaarNo,updatedUser.getAadhaarNo());
        assertEquals(updatedPanNo,updatedUser.getPanNo());
        assertEquals(updatedAccountType,updatedUser.getAccountType());
        assertEquals(updatedAddress,updatedUser.getAddress());
        assertEquals(updatedDob,updatedUser.getDob());

        assertNotNull(retrievedUser);
        assertEquals(updatedFirstName, retrievedUser.getFirstName());
        assertEquals(updatedLastName, retrievedUser.getLastName());
        assertEquals(updatedEmail, retrievedUser.getEmail());
        assertEquals(updatedContactNo, retrievedUser.getContactNo());
        assertEquals(updatedPin, retrievedUser.getPin());
        assertEquals(updatedAadhaarNo,retrievedUser.getAadhaarNo());
        assertEquals(updatedPanNo,retrievedUser.getPanNo());
        assertEquals(updatedAccountType,retrievedUser.getAccountType());
        assertEquals(updatedAddress,retrievedUser.getAddress());
        assertEquals(updatedDob,retrievedUser.getDob());

    }
}