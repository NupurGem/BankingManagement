package com.example.BankingManagement.controller;

import com.example.BankingManagement.dto.BankAccountDto;
import com.example.BankingManagement.model.BankAccount;
import com.example.BankingManagement.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BankAccountController {


    private final BankAccountService bankAccountService;
    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    public BankAccountController(BankAccountService bankAccountService)
    {

        this.bankAccountService= bankAccountService;
    }


    @DeleteMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable long id) {
        logger.info("Deleting customer with ID: {}", id);
        bankAccountService.deleteCustomer(id);
        logger.info("Customer with ID {} deleted successfully", id);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<BankAccountDto> getCustomer(@PathVariable long id){
        logger.info("Fetching customer with ID: {}", id);

        //return bankAccountService.getCustomer(id);
        BankAccountDto bankAccountDto = bankAccountService.getCustomer(id);

        if (bankAccountDto == null) {
            logger.warn("Customer with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Customer with ID {} found", id);
        return ResponseEntity.ok(bankAccountDto);
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccount createCustomer(@RequestBody BankAccount bankAccount){
        logger.info("Creating a new customer");
       return  bankAccountService.create(bankAccount);
    }




}

