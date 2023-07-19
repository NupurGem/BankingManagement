package com.example.BankingManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Entity
@Transactional
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "customerDetails")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "contact_No", nullable = false)
    private String contactNo;

    @Column(name = "dob", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "pin")
    private String pin;

    @Column(name = "aadhaar_No")
    private long aadhaarNo;

    @Column(name = "pan_No")
    private String panNo;

    @Column(name = "accountType")
    private String accountType;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<AdminDetails> adminDetails;


}


