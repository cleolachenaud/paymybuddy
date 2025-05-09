package com.openclassroom.paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.ITransactionsRepository;
import com.openclassroom.paymybuddy.service.TransactionsService;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
	
	private static final Logger logger = LogManager.getLogger("TransactionsController");
	
	@Autowired
    TransactionsService transactionService;
	
	@Autowired
	ITransactionsRepository transactionRepository;
	

    @PostMapping("/send")
    public ResponseEntity<String> transferMoney(@RequestParam int userSenderId,
                                            @RequestParam int userReceiverId,
                                            @RequestParam double montant,
                                            @RequestParam String description) {
        try {
            transactionService.transferMoney(userSenderId, userReceiverId, montant, description);
        } catch (RuntimeException e) {
        	logger.error("transferMoney. Transfert d'argent n'a pas abouti");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
        logger.info("transferMoney. Transfert d'argent réussi");
        return ResponseEntity.ok("Transfert d'argent réussi !");
        
    }
    /*
    @GetMapping("/user/{userId}")
    public ResponseEntity <List<Transactions>> getTransactionsByUserId(@RequestParam int userSenderId) {
    	List<Transactions> transactions = new ArrayList();
    	try {
    		transactions = transactionService.getTransactionsByUser(userSenderId);
    	} catch (RuntimeException e) {
    		logger.error("getTransaction. affichage de l historique n'a pas abouti");
    		return ResponseEntity.notFound().build();
    	}
    	logger.info("getTransactions. historique des transactions réussi");
    	return ResponseEntity.ok(transactions);
    	}
    	*/
	}
