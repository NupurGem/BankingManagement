package com.example.BankingManagement.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Transactional
@Data
@NoArgsConstructor
@AllArgsConstructor


@Table(name = "transactionDetails")
public class AdminDetails {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "accountNo",nullable = false)
    private long accountNo;

    @Column(name ="balance",nullable = false)
    private double balance;

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date = LocalDateTime.now();


    @Column(name="sourceId")
    private long sourceId;

    @Column(name = "target_id")
    private long targetId;

    @Column(name = "transaction_amount")
    private double transactionAmount;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AdminDetails(long id, long transactionAmount) {
        this.id = id;
        this.transactionAmount= transactionAmount;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private BankAccount bankAccount;

}
