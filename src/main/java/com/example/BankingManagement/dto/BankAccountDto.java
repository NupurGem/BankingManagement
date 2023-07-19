package com.example.BankingManagement.dto;

public class BankAccountDto {
    String firstName;

    String lastName;
    String email;
    String contactNo;
    String address;

    public BankAccountDto(String firstName, String lastName,String email, String contactNo, String address) {
        this.firstName= firstName;
        this.lastName= lastName;
        this.email = email;
        this.contactNo =contactNo;
        this.address=address;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getAddress() {
        return address;
    }

    public String getLastName() {
        return lastName;
    }


    //double getBalance();
}
