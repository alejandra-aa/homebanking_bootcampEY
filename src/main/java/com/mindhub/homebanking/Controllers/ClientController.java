package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @RequestMapping(value = "/clients/{id}", method= RequestMethod.GET)
    public Optional<ClientDTO> getClient(@PathVariable long id){
        return Optional.ofNullable(clientRepository.findById(id).map(ClientDTO::new).orElse(null));
    }

    @RequestMapping(value = "/clients/find/{email}", method= RequestMethod.GET)
    public ClientDTO getClientByEmail(@PathVariable String email){
        if(this.clientRepository.findByEmail(email) == null){
            return null;
        }
        return new ClientDTO(this.clientRepository.findByEmail(email)) ;
    }

    @RequestMapping(value = "/clients/findBy/{name}", method = RequestMethod.GET)
    public List<ClientDTO> getClientByName(@PathVariable String name){
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return this.clientRepository.findAllByNameIsLikeOrderByLastNameAsc(name).stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClients(Authentication authentication){
        Client c = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(c);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String name, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(name, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        String number = "VIN" + (int)(Math.random() * (100000000 - 1) + 1);
        LocalDateTime date = LocalDateTime.now();
        double balance = 0;

        Account acc = new Account(number, date, balance, client);
        accountRepository.save(acc);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
