package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    @RequestMapping(value = "/transactions/{id}", method= RequestMethod.GET)
    public Optional<TransactionDTO> getTransaction(@PathVariable long id){
        return transactionRepository.findById(id).map(TransactionDTO::new);
    }

    @Transactional
    @RequestMapping(value="/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> makeTransaction(Authentication authentication, @RequestParam  double amount,
                                                  @RequestParam String description, @RequestParam String fromAccountNumber,
                                                  @RequestParam String toAccountNumber){

        Client client = this.clientRepository.findByEmail(authentication.getName());
        Account accountOrigen = this.accountRepository.findByNumber(fromAccountNumber);
        Account accountDestino = this.accountRepository.findByNumber(toAccountNumber);

        //verificar que las cuentas existan
        if(accountDestino == null || accountOrigen == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        //verificar que los campos no esten vacíos
        else if(description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty() || amount <= 0){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        //verificar que las cuentas no sean iguales
        else if(toAccountNumber == fromAccountNumber){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //verificar que la cuenta de origen tenga el monto solicitado
        else if(accountOrigen.getBalance() < amount){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //verificar que la cuenta de origen sea la del cliente
        Set<Account> clientAccounts = client.getAccounts();
        for(Account acc : clientAccounts){
            if(acc == accountOrigen){
                //crea transacciones y actualiza balances de cuentas
                Transaction transactionOrigen = new Transaction(TransactionType.DEBIT, -amount, description, LocalDateTime.now(), accountOrigen);
                Transaction transactionDestino = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now(), accountDestino);
                transactionRepository.save(transactionOrigen);
                transactionRepository.save(transactionDestino);
                accountOrigen.setBalance(accountOrigen.getBalance() - amount);
                accountDestino.setBalance(accountDestino.getBalance() + amount);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
