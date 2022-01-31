package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.ClientLoan;

public class ClientLoanDTO {
    private long clientId;
    private long loanId;
    private String name;
    private double amount;
    private int payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.clientId = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayment();
    }

    public long getId() {
        return clientId;
    }

    public void setId(long id) {
        this.clientId = id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
}
