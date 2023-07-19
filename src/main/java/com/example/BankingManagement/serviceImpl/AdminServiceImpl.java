package com.example.BankingManagement.serviceImpl;

import com.example.BankingManagement.exception.ResourceNotFoundException;
import com.example.BankingManagement.model.AdminDetails;
import com.example.BankingManagement.repository.AdminRepository;
import com.example.BankingManagement.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(AdminRepository adminRepository) {

        this.adminRepository = adminRepository;
    }

    @Override
    public AdminDetails create(AdminDetails adminDetails) {
        logger.info("Creating a new adminDetails");
        return adminRepository.save(adminDetails);
    }

    @Override
    public Double getBalance(long accountNo) {
        logger.info("Fetching balance for account with account number: {}", accountNo);
        Double account = adminRepository.getAccountByNumber(accountNo);
        if (account != null) {
            return account;
        } else {
            logger.error("Account with account number {} not found", accountNo);
            throw new IllegalArgumentException("Account not found.");
        }
    }

    @Override
    public void depositAmount(long id, double transactionAmount) {
        logger.info("Depositing amount {} into account with ID: {}", transactionAmount, id);
        adminRepository.saveTransactionAmountByID(id, transactionAmount);
    }

    @Override
    public void updateBalance(long Account_id, double newBalance) {
        logger.info("Updating balance for account with ID: {}. New balance: {}", Account_id, newBalance);
        Optional<AdminDetails> optionalAdmin = adminRepository.findById(Account_id);
        if (optionalAdmin.isPresent()) {
            AdminDetails admin = optionalAdmin.get();
            admin.setBalance(newBalance);
            adminRepository.save(admin);
        } else {
            logger.error("Account with ID {} not found", Account_id);
            throw new IllegalArgumentException("Account not found.");
        }
    }


    @Override
    public void withdrawAmount(long id, double transactionAmount) {
        logger.info("Withdrawing amount {} from account with ID: {}", transactionAmount, id);
        Optional<AdminDetails> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            AdminDetails admin = optionalAdmin.get();
            double currentBalance = admin.getBalance();
            if (currentBalance >= transactionAmount) {
                double newBalance = currentBalance - transactionAmount;
                updateBalance(id, newBalance);
                logger.info("Amount {} withdrawn from account with ID: {}. New balance: {}", transactionAmount, id, newBalance);
            } else {
                logger.error("Insufficient funds for withdrawal from account with ID: {}", id);
                throw new IllegalArgumentException("Insufficient funds.");
            }
        } else {
            logger.error("Account with ID {} not found", id);
            throw new IllegalArgumentException("Account not found.");
        }

    }

@Override
@Transactional
public double transferAmount(long sourceId, long targetId, double transactionAmount) {
    logger.info("Transferring amount {} from account with ID: {} to account with ID: {}", transactionAmount, sourceId, targetId);
    Optional<AdminDetails> optionalSender = adminRepository.findById(sourceId);
    Optional<AdminDetails> optionalRecipient = adminRepository.findById(targetId);

    if (optionalSender.isPresent() && optionalRecipient.isPresent()) {
        AdminDetails sender = optionalSender.get();
        AdminDetails recipient = optionalRecipient.get();

        double senderBalance = sender.getBalance();
        if (senderBalance >= transactionAmount) {
            double recipientBalance = recipient.getBalance();

            double senderNewBalance = senderBalance - transactionAmount;
            double recipientNewBalance = recipientBalance + transactionAmount;
            sender.setBalance(senderNewBalance);
            recipient.setBalance(recipientNewBalance);

            adminRepository.save(sender);
            adminRepository.save(recipient);

            System.out.println("Funds transferred successfully!");
            System.out.println("Balance: " + senderNewBalance);
            logger.info("Funds transferred successfully!");
            logger.info("Sender balance after transfer: {}", senderNewBalance);

            // Update AdminDetails with transfer details
            sender.setSourceId(sourceId);
            sender.setTargetId(targetId);
            sender.setTransactionAmount(transactionAmount);

            recipient.setSourceId(sourceId);
            recipient.setTargetId(targetId);
            recipient.setTransactionAmount(transactionAmount);

            adminRepository.save(sender);
            adminRepository.save(recipient);

            return senderNewBalance; // Return the sender's new balance
        } else {
            logger.error("Insufficient funds for transfer from account with ID: {}", sourceId);
            throw new IllegalArgumentException("Insufficient funds.");
        }
    } else {
        logger.error("One or both accounts not found while transferring amount");
        throw new IllegalArgumentException("One or both accounts not found.");
    }
}

    @Override
    public void deleteAccount(long id) {
        logger.info("Deleting account with ID: {}", id);
        adminRepository.deleteById(id);
        logger.info("Account with ID {} deleted successfully", id);
    }

    @Override
    public AdminDetails getCustomerDetails(long id) {
//        Optional<AdminDetails> adminDetailsOptional= adminRepository.findById(id);
//        if(adminDetailsOptional.isPresent())
//            return adminDetailsOptional.get();
//        else {
//            throw new IllegalArgumentException("Account not found.");
//
//        }
//    }
        logger.info("Fetching customer details for ID: {}", id);
        Optional<AdminDetails> adminDetailsOptional = adminRepository.findById(id);
        return adminDetailsOptional.orElseThrow(() ->
                new ResourceNotFoundException("AdminDetails", "id", id));
    }
}

