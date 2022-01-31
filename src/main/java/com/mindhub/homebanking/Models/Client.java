package com.mindhub.homebanking.Models;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client(){
    }

    public Client(String name, String lastName, String email, String password){
        this.name=name;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }

    //getters & setters
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public Set<Account> getAccounts() {
        return accounts;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }
    public void addCard(Card card){
        card.setClient(this);
        this.cards.add(card);
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getId() == client.getId() && Objects.equals(getName(), client.getName()) && Objects.equals(getLastName(), client.getLastName()) && Objects.equals(getEmail(), client.getEmail()) && Objects.equals(getAccounts(), client.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName(), getEmail(), getAccounts());
    }
     */
}
