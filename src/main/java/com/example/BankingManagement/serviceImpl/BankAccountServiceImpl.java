package com.example.BankingManagement.serviceImpl;

import com.example.BankingManagement.dto.BankAccountDto;
import com.example.BankingManagement.exception.ResourceNotFoundException;
import com.example.BankingManagement.model.BankAccount;
import com.example.BankingManagement.repository.BankAccountRepository;
import com.example.BankingManagement.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {


    private BankAccountRepository bankAccountRepository;
    private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);


    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {
        logger.info("Creating a new bank account");
        return bankAccountRepository.save(bankAccount);
    }


    @Override
    public BankAccountDto getCustomer(long id) throws ResourceNotFoundException
    {
      //  return bankAccountRepository.findProjectedById(id);
        logger.info("Fetching customer with ID: {}", id);
        BankAccountDto bankAccountDto = bankAccountRepository.findProjectedById(id);
        if (bankAccountDto == null) {
            logger.error("Bank Account with ID {} not found", id);
            throw new ResourceNotFoundException("Bank Account", "id", id);
        }
        return bankAccountDto;
    }

    @Override
    public void deleteCustomer(long id) {
        logger.info("Deleting customer with ID: {}", id);
        BankAccount bankAccount= bankAccountRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Bank Account", "id", id));
        bankAccountRepository.delete(bankAccount);
        logger.info("Customer with ID {} deleted successfully", id);
    }

    @Override
    public BankAccount updateUser(BankAccount user) throws ResourceNotFoundException
    {
        logger.info("Updating user with ID: {}", user.getId());
        return bankAccountRepository.save(user);
    }

}
