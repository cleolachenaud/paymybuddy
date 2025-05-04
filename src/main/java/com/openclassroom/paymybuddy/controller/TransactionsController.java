package com.openclassroom.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.service.TransactionsService;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
	
	@Autowired
    private TransactionsService transactionService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMoney(@RequestParam int userSenderId,
                                            @RequestParam int userReceiverId,
                                            @RequestParam double montant) {
        try {
            transactionService.transferMoney(userSenderId, userReceiverId, montant);
            return ResponseEntity.ok("Transfert d'argent réussi !");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }

}
