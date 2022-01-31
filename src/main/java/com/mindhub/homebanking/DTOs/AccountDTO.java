package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;
import java.time.LocalDateTime;
import java.util.Set;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<Transaction> transactions;

    public AccountDTO(Account account){
        this.id = account.getId();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.number = account.getNumber();
        this.transactions = account.getTransactions();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
