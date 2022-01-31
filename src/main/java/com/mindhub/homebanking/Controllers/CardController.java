package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.css.Counter;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @RequestMapping(value= "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardColor cardColor, @RequestParam CardType cardType){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        //if(client.getCards().stream().filter(card -> card.getType() == cardType) )
        if(client.getCards().stream().filter(card -> card.getType() == cardType).count() >=3){
            return new ResponseEntity<>("Exceed limit of " + cardType + " cards", HttpStatus.FORBIDDEN);
        }
        String cardHolder = client.getName() + client.getLastName();
        Random rand = new Random();
        int random1 = rand.nextInt((9999 - 100) + 1) + 10;
        int random2 = rand.nextInt((9999 - 100) + 1) + 10;
        int random3 = rand.nextInt((9999 - 100) + 1) + 10;
        int random4 = rand.nextInt((9999 - 100) + 1) + 10;

        String number = random1 + "-" + random2 + "-" + random3 + "-" + random4;
        int cvv = rand.nextInt(900) + 100;

        Card card = new Card(cardHolder, (CardType) cardType, (CardColor) cardColor, number, cvv, LocalDateTime.now().plusYears(5), LocalDateTime.now(), client);
        cardRepository.save(card);

        return new ResponseEntity<>("Created succesfully", HttpStatus.CREATED);
    }

}
