package com.example.BankingManagement.service;

import com.example.BankingManagement.model.AdminDetails;

public interface AdminService {


    AdminDetails create(AdminDetails adminDetails);

    //Optional<AdminDetails> getCustomer(long id);

    Double getBalance(long accountNo);

    void withdrawAmount(long id, double transactionAmount);


    double transferAmount(long sourceId, long targetId, double transactionAmount);

    void depositAmount(long id, double transactionAmount);


    void deleteAccount(long id);

    AdminDetails getCustomerDetails(long id);

    void updateBalance(long Account_id, double newBalance);

}
