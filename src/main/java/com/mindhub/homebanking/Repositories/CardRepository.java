package com.mindhub.homebanking.Repositories;

import com.mindhub.homebanking.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>  {


}