package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public AccountDTO getAccount(@PathVariable Long id, Authentication authentication){

        Client client = this.clientRepository.findByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();

        Iterator iter = accounts.iterator();
        while (iter.hasNext()){
            Account acc = (Account) iter.next();
            if (acc.getId() == id){
                return this.accountRepository.findById(id).map(AccountDTO::new).orElse(null);
            }
        }
        return null;
    }

    @RequestMapping(value= "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size() >= 3 ){
            return new ResponseEntity<>("Exceed limit of accounts", HttpStatus.FORBIDDEN);
        }
        String number = "VIN" + (int)(Math.random() * (100000000 - 1) + 1);
        LocalDateTime date = LocalDateTime.now();
        double balance = 0;

        Account acc = new Account(number, date, balance, client);
        accountRepository.save(acc);
        return new ResponseEntity<>("Created succesfully", HttpStatus.CREATED);
    }

    @RequestMapping(value= "/clients/current/accounts", method = RequestMethod.GET)
    public Set<Account> getCurrentAccounts(Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getAccounts();
    }
/*
    @RequestMapping(value = "/accounts/{number}", method = RequestMethod.GET)
    public AccountDTO getAccountByNumber(@PathVariable String number){
        Account acc = accountRepository.findByNumber(number);
        return new AccountDTO(acc);
    }

 */
}
