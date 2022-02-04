package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of clients
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("abc"));
			clientRepository.save(client1);
			Client client2 = new Client("Mateo", "Azua", "mateo@gmail.com", passwordEncoder.encode("def"));
			clientRepository.save(client2);

			Account acc1 = new Account("VIN001", LocalDateTime.now(), 5000, client1);
			accountRepository.save(acc1);
			Account acc2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500, client1);
			accountRepository.save(acc2);
			Account acc3 = new Account("VIN003", LocalDateTime.now(), 6500, client2);
			accountRepository.save(acc3);
			Account acc4 = new Account("VIN004", LocalDateTime.now().plusDays(1), 10000, client2);
			accountRepository.save(acc4);

			Transaction t1 = new Transaction(TransactionType.DEBIT, -2000, "Transaccion 1", LocalDateTime.now(), acc1);
			transactionRepository.save(t1);
			Transaction t2 = new Transaction(TransactionType.DEBIT, -1000, "Transaccion 2", LocalDateTime.now(), acc1);
			transactionRepository.save(t2);
			Transaction t3 = new Transaction(TransactionType.CREDIT, 500, "Transaccion 3", LocalDateTime.now(), acc1);
			transactionRepository.save(t3);
			Transaction t4 = new Transaction(TransactionType.CREDIT, 3000, "Transaccion 4", LocalDateTime.now(), acc2);
			transactionRepository.save(t4);
			Transaction t5 = new Transaction(TransactionType.CREDIT, 2000, "Transaccion 5", LocalDateTime.now(), acc2);
			transactionRepository.save(t5);
			Transaction t6 = new Transaction(TransactionType.CREDIT, 4000, "Transaccion 6", LocalDateTime.now(), acc3);
			transactionRepository.save(t6);
			Transaction t7 = new Transaction(TransactionType.DEBIT, -2000, "Transaccion 7", LocalDateTime.now(), acc3);
			transactionRepository.save(t7);
			Transaction t8 = new Transaction(TransactionType.CREDIT, 3500, "Transaccion 8", LocalDateTime.now(), acc4);
			transactionRepository.save(t8);
			Transaction t9 = new Transaction(TransactionType.DEBIT, -1000, "Transaccion 9", LocalDateTime.now(), acc4);
			transactionRepository.save(t9);
			Transaction t10 = new Transaction(TransactionType.DEBIT, -4000, "Transaccion 10", LocalDateTime.now(), acc4);
			transactionRepository.save(t10);

			Loan l1 = new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60));
			loanRepository.save(l1);
			Loan l2 = new Loan("Personal", 100000, List.of(6, 12, 24));
			loanRepository.save(l2);
			Loan l3 = new Loan("Automotriz", 300000, List.of(6, 12, 24, 36));
			loanRepository.save(l3);

			ClientLoan cl1 = new ClientLoan(400000, 60, client1, l1);
			clientLoanRepository.save(cl1);
			ClientLoan cl2 = new ClientLoan(50000, 6, client1, l2);
			clientLoanRepository.save(cl2);
			ClientLoan cl3 = new ClientLoan(100000, 24, client2, l2);
			clientLoanRepository.save(cl3);
			ClientLoan cl4 = new ClientLoan(200000, 36, client2, l3);
			clientLoanRepository.save(cl4);

			Card card1= new Card(client1.getName() + " " + client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "1289-4692-4725-3891", 628, LocalDateTime.now().plusYears(5), LocalDateTime.now(), client1);
			cardRepository.save(card1);
			Card card2= new Card(client1.getName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "8364-2839-2163-8932", 321, LocalDateTime.now().plusYears(5), LocalDateTime.now(), client1);
			cardRepository.save(card2);
			Card card3= new Card(client2.getName() + " " + client2.getLastName(), CardType.CREDIT, CardColor.SILVER, "2718-3943-2673-3784", 780, LocalDateTime.now().plusYears(5), LocalDateTime.now(), client2);
			cardRepository.save(card3);


		};
	}



 */

}
