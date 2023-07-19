package com.example.BankingManagement.controller;

import com.example.BankingManagement.exception.ResourceNotFoundException;
import com.example.BankingManagement.model.AdminDetails;
import com.example.BankingManagement.service.AdminService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "Admin")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @GetMapping("/transaction/{id}")
    public AdminDetails getCustomerDetails(@PathVariable long id){
        logger.info("Fetching customer details for ID: {}", id);
        Optional<AdminDetails> adminDetails = Optional.ofNullable(adminService.getCustomerDetails(id));
        if (adminDetails.isPresent()) {
            logger.info("Customer details found for ID: {}", id);
            return adminDetails.get();
        } else {
            logger.warn("Customer details not found for ID: {}", id);
            throw new ResourceNotFoundException("AdminDetails", "id", id);
        }
    }

    @DeleteMapping("/transaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable long id) {
        logger.info("Deleting account with ID: {}", id);
        adminService.deleteAccount(id);
        logger.info("Account with ID {} deleted successfully", id);
    }

    @PutMapping("/transaction/{id}/deposit/{transactionAmount}")
    public void depositAmount(@PathVariable long id , @PathVariable long transactionAmount) {
        logger.info("Depositing amount {} into account with ID: {}", transactionAmount, id);
        adminService.depositAmount(id, transactionAmount);
        logger.info("Amount {} deposited into account with ID: {}", transactionAmount, id);
    }


    @PutMapping("/transaction/{id}/withdraw/{transactionAmount}")
    public void withdrawAmount(@PathVariable long id, @PathVariable double transactionAmount) {
        logger.info("Withdrawing amount {} from account with ID: {}", transactionAmount, id);
        adminService.withdrawAmount(id, transactionAmount);
        logger.info("Amount {} withdrawn from account with ID: {}", transactionAmount, id);
    }

    @PutMapping("/transaction/{sourceId}/transfer/{targetId}/{transactionAmount}")
    public ResponseEntity<Double> transferAmount(@PathVariable long sourceId,@PathVariable long targetId, @PathVariable long transactionAmount)
    {
        try {
            logger.info("Transferring amount {} from account with ID: {} to account with ID: {}", transactionAmount, sourceId, targetId);
            double senderBalance = adminService.transferAmount(sourceId, targetId, transactionAmount);
            logger.info("Amount {} transferred from account with ID: {} to account with ID: {}. Sender balance: {}", transactionAmount, sourceId, targetId, senderBalance);
            return ResponseEntity.ok(senderBalance);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid transfer request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);

        } catch (ResourceNotFoundException e) {
            logger.warn("Resource not found while transferring amount: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/{accountNo}/balance")
    public double getBalance(@PathVariable long accountNo) {
        logger.info("Fetching balance for account with account number: {}", accountNo);
        // extra balance added
        double balance = adminService.getBalance(accountNo);
        logger.info("Balance for account with account number {} is: {}", accountNo, balance);
        return adminService.getBalance(accountNo);

    }



}

