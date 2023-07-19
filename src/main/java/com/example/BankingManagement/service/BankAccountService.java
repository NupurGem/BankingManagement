package com.example.BankingManagement.service;

import com.example.BankingManagement.dto.BankAccountDto;
import com.example.BankingManagement.model.BankAccount;

public interface BankAccountService {

    BankAccount create(BankAccount bankAccount);

    BankAccountDto getCustomer(long id);

    BankAccount updateUser(BankAccount user);

    void deleteCustomer(long id);
}
