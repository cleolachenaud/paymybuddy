package com.openclassroom.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private static final Logger logger = LogManager.getLogger("TransactionsService");
	@Transactional
    public List<Transactions> getTransactionsByUser(int userSenderId) {
    	Users user = usersRepository.findById(userSenderId);
        return transactionsRepository.findAllByUserSender(user);
    }
	
    @Transactional
    public void transferMoney(int userSenderId, int userReceiverId, double montantAPayer, String description) {
    	logger.debug("entrée dans la methode transferMoney");
    	// je vérifie que le montant est supérieur à zéro
        if (montantAPayer < 0) {
        	logger.error("Le montant doit être supérieur à zéro.");
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro.");
        }
        if(description == null) {
        	logger.error("une description est requise");
        	throw new IllegalArgumentException("une description est requise");
        }
        // je vérifie que les utilisateurs existent et qu'ils ont bien un compte sur l'application
        Users userSender = usersRepository.findById(userSenderId);
        
        Users userReceiver = usersRepository.findById(userReceiverId);
        Compte sender = compteRepository.findByUserCompteId(userSender)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Compte receiver = compteRepository.findByUserCompteId(userReceiver)
            .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));
        
        // je vérifie que les utilisateurs sont en relation      
        if (!usersLinkRepository.existsByUser1AndUser2(userSender, userReceiver)) {
        	logger.error("Les utilisateurs ne sont pas en relation");
            throw new RuntimeException("Les utilisateurs ne sont pas en relation.");
        }
        
        // je vérifie que l'utilisateur qui doit payer à assez de sous sur son compte
        if (sender.getSoldeCompte() < montantAPayer) {
        	logger.error("Solde insuffisant pour cette transaction.");
            throw new RuntimeException("Solde insuffisant pour cette transaction.");
        }
        
        // j'enregistre la transaction
        Transactions transactionEnCours = new Transactions();
        transactionEnCours.setUserSenderId(userSender);
        transactionEnCours.setUserRecieverId(userReceiver);
        transactionEnCours.setMontant(montantAPayer);
        transactionEnCours.setDescription(description);
        transactionsRepository.save(transactionEnCours);
        logger.debug("transaction insérée en base");
        
        // j'enlève les sous sur le compte débiteur (sender)
        sender.setSoldeCompte(sender.getSoldeCompte() - montantAPayer );
        compteRepository.save(sender); // j'enregistre le nouveau montant du compte (avec le débit du montantAPayer)
        logger.debug("nouveau montant du compte débiteur inséré en base");

        // je crédite les sous sur le compte créditeur (reciever)
        receiver.setSoldeCompte(receiver.getSoldeCompte() + montantAPayer);
        compteRepository.save(receiver); // j'enregistre le montant du compte (avec le crédit du montantAPayer) 
        logger.debug("nouveau montant du compte créditeur inséré en base");
        logger.debug("fin de la méthode transferMoney");
    }
}
