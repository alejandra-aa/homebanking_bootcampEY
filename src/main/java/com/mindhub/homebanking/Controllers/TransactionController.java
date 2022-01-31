package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }
    @RequestMapping(value = "/transactions/{id}", method= RequestMethod.GET)
    public Optional<TransactionDTO> getTransaction(@PathVariable long id){
        return transactionRepository.findById(id).map(TransactionDTO::new);
    }
}
