package com.mindhub.homebanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account(){
    }

    public Account(String number, LocalDateTime creationDate, double balance, Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
    }

    //getters & setters
    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public double getBalance() {
        return balance;
    }
    @JsonIgnore
    public Client getClient() {
        return client;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId() == account.getId() && Double.compare(account.getBalance(), getBalance()) == 0 && Objects.equals(getNumber(), account.getNumber()) && Objects.equals(getCreationDate(), account.getCreationDate()) && Objects.equals(getClient(), account.getClient()) && Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getCreationDate(), getBalance(), getClient(), transactions);
    }
     */
}
