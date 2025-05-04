package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.ICompteRepository;
import com.openclassroom.paymybuddy.repository.ITransactionsRepository;
import com.openclassroom.paymybuddy.repository.IUsersLinkRepository;
import com.openclassroom.paymybuddy.repository.IUsersRepository;

@Service
public class TransactionsService {
	
	@Autowired
	ITransactionsRepository transactionsRepository;
	
	@Autowired
	IUsersRepository usersRepository;
	
	@Autowired
	ICompteRepository compteRepository;
	
	@Autowired
	Compte compte;
	
	@Autowired
	IUsersLinkRepository usersLinkRepository;
	
	public Iterable<Transactions> getTransactions (int userId) {
		return transactionsRepository.findAllByUser(userId);//si jamais cette méthode plante, ya moyen qu'il faille mettre comme nom findAllByUserId
	}
	
    @Transactional
    public void transferMoney(int userSender, int userReceiver, double montantAPayer) {
    	
    	// je vérifie que le montant est supérieur à zéro
        if (montantAPayer < 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro.");
        }
        
        // je vérifie que les utilisateurs existent et qu'ils ont bien un compte sur l'application
        Users userSenderId = usersRepository.findById(userSender);
        Users userReceiverId = usersRepository.findById(userReceiver);
        Compte sender = compteRepository.findById(userSenderId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Compte receiver = compteRepository.findById(userReceiverId)
            .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));
        
        // je vérifie que les utilisateurs sont en relation      
        if (!usersLinkRepository.existsByUser1AndUser2(userSenderId, userReceiverId)) {
            throw new RuntimeException("Les utilisateurs ne sont pas en relation.");
        }
        
        // je vérifie que l'utilisateur qui doit payer à assez de sous sur son compte
        if (sender.getSoldeCompte() < montantAPayer) {
            throw new RuntimeException("Solde insuffisant pour cette transaction.");
        }
        
        // j'enregistre la transaction
        Transactions transactionEnCours = new Transactions();
        transactionEnCours.setUserSenderId(userSenderId);
        transactionEnCours.setUserRecieverId(userReceiverId);
        transactionEnCours.setMontant(montantAPayer);
        transactionsRepository.save(transactionEnCours);
        
        // j'enlève les sous sur le compte débiteur (sender)
        sender.setSoldeCompte(sender.getSoldeCompte() - montantAPayer );
        compteRepository.save(sender); // j'enregistre le nouveau montant du compte (avec le débit du montantAPayer)

        // je crédite les sous sur le compte créditeur (reciever)
        receiver.setSoldeCompte(receiver.getSoldeCompte() + montantAPayer);
        compteRepository.save(receiver); // j'enregistre le montant du compte (avec le crédit du montantAPayer) 
    }
}
