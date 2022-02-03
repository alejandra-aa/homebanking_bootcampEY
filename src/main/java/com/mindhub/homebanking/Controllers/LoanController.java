package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.LoanApplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value="/loans", method = RequestMethod.GET)
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @Transactional
    @RequestMapping(value="/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(Authentication authentication,
                                             @RequestBody LoanApplicationDTO loan){

        Client client = this.clientRepository.findByEmail(authentication.getName());
        Account clientAccount = null;

        //verificar que los datos no esten vacios
        if(loan.getAmount() <= 0 || loan.getPayments() <=0 || loan.getToAccountNumber().isEmpty()){
            return new ResponseEntity<>("Datos vacíos", HttpStatus.FORBIDDEN);
        }

        double amountPlus20 = loan.getAmount() + ((loan.getAmount()*20)/100);

        Optional<Loan> solicitado = loanRepository.findById(loan.getLoanId());

        //verificar que el prestamo exista
        if(solicitado.isEmpty() || solicitado == null){
            return new ResponseEntity<>("No existe prestamo solicitado", HttpStatus.FORBIDDEN);
        }

        //verificar que el monto no exceda el maximo
        if(loan.getAmount() > solicitado.get().getMaxAmount()){
            return new ResponseEntity<>("Monto excede máximo", HttpStatus.FORBIDDEN);
        }

        //verificar que la cuenta pertenezca al cliente autenticado
        Set<Account> clientAccounts = client.getAccounts();
        for(Account acc : clientAccounts){
            if(Objects.equals(acc.getNumber(), loan.getToAccountNumber())){
                clientAccount = acc;
                //crear solicitud sumando el 20%
                ClientLoan createCL = new ClientLoan(amountPlus20, loan.getPayments(), client, solicitado.get());
                clientLoanRepository.save(createCL);
                //crear transaccion credit para agregar el prestamo solicitado
                Transaction createT = new Transaction(TransactionType.CREDIT, loan.getAmount(), "Prestamo " + solicitado.get().getName(), LocalDateTime.now(), clientAccount);
                transactionRepository.save(createT);
                //actualizar cuenta de destino con el monto del prestamo
                clientAccount.setBalance(clientAccount.getBalance() + loan.getAmount());

                return new ResponseEntity<>(HttpStatus.CREATED);

            }
        }
        return new ResponseEntity<>("Final", HttpStatus.FORBIDDEN);
    }

}
