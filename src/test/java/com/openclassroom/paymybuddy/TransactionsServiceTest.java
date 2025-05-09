package com.openclassroom.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.ICompteRepository;
import com.openclassroom.paymybuddy.repository.ITransactionsRepository;
import com.openclassroom.paymybuddy.repository.IUsersLinkRepository;
import com.openclassroom.paymybuddy.repository.IUsersRepository;
import com.openclassroom.paymybuddy.service.TransactionsService;


public class TransactionsServiceTest {
	  @InjectMocks
	    private TransactionsService transactionService; // Classe que je test

	    @Mock
	    private IUsersRepository usersRepository;

	    @Mock
	    private ICompteRepository compteRepository;

	    @Mock
	    private ITransactionsRepository transactionsRepository;

	    @Mock
	    private IUsersLinkRepository usersLinkRepository;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	@Test
	public void testGetTransactionsByUserOk() { 
		Users userSender = new Users();
        userSender.setUserId(1);
        Users userReciever = new Users();
        userReciever.setUserId(2);
        // création de deux transactions pour simuler une antériorité en base
        Transactions transaction1 = createTransaction(userSender, userReciever, 1, 10.0, "transaction1");
        Transactions transaction2 = createTransaction(userSender, userReciever, 2, 20.0, "transaction2");
        List<Transactions> listTransactions = new ArrayList();
        listTransactions.add(0,transaction1);
        listTransactions.add(1,transaction2);
        // je vérifie que ma méthode retourne bien les transactions avec l'ID 
        when(usersRepository.findById(1)).thenReturn(Optional.of(userSender));
        when(transactionsRepository.findAllByUserSenderId(userSender)).thenReturn(listTransactions);
        List<Transactions> result = transactionService.getTransactionsByUser(1);
        assertEquals(listTransactions, result);
	}
	@Test
	public void testGetTransactionsByUserUtilisateurNonTrouve() {
		when(usersRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.getTransactionsByUser(1);
        });
        assertEquals("utilisateur inconnu", exception.getMessage());
	}


	    
	@Test
    public void testTransferMoneyOk() {
        // Configuration des utilisateurs et comptes pour un transfert réussi
        Users userSender = new Users();
        userSender.setUserId(1);
        
        Users userReceiver = new Users();
        userReceiver.setUserId(2);
        
        Compte senderCompte = new Compte();
        senderCompte.setSoldeCompte(200.0);
        
        Compte receiverCompte = new Compte();
        receiverCompte.setSoldeCompte(100.0);

        when(usersRepository.findById(1)).thenReturn(Optional.of(userSender));
        when(usersRepository.findById(2)).thenReturn(Optional.of(userReceiver));
        when(compteRepository.findByUserCompteId(userSender)).thenReturn(Optional.of(senderCompte));
        when(compteRepository.findByUserCompteId(userReceiver)).thenReturn(Optional.of(receiverCompte));
        when(usersLinkRepository.existsByUserSenderIdAndUserRecieverId(userSender, userReceiver)).thenReturn(true);
        // Appel de la méthode à tester
        transactionService.transferMoney(1, 2, 100, "remboursement");

        // Vérification
        verify(transactionsRepository).save(any(Transactions.class));
        assertEquals(100.0, senderCompte.getSoldeCompte(), 100.0); // Vérifiez le solde du sender
        assertEquals(200.0, receiverCompte.getSoldeCompte(), 200.0); // Vérifiez le solde du receiver
    }
	@Test
	public void testTransferMoneyMontantInvalide() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferMoney(1, 2, -10, "Description");
        });
        assertEquals("Le montant doit être supérieur à zéro.", exception.getMessage());
	}
	@Test
	public void testTransferMoneyDescriptionNull() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferMoney(1, 2, 10, null);
        });
        assertEquals("une description est requise", exception.getMessage());
	}
	@Test
	public void testTransferMoneyUtilisateurNonTrouve() {
		when(usersRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney(1, 2, 10, "Description");
        });
        assertEquals("utilisateur inconnu", exception.getMessage());
	}
	
	@Test
	public void testTransferMoneyRelationInexistante() {
		createUtilisateursEtRelations(10.0, 10.0, false);
		Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney(1, 2, 2, "Description");
        });
        assertEquals("Les utilisateurs ne sont pas en relation.", exception.getMessage());
	}
	
	@Test
	public void testTransferMoneyFondsInsuffisants() {
		
		//je crée mes utilisateurs et leurs relations
		createUtilisateursEtRelations(10.0, 10.0, true);
	        
	     // Appel de la méthode à tester
		Exception exception = assertThrows(RuntimeException.class, () -> {
	        transactionService.transferMoney(1, 2, 100, "Description");
	    });
	    assertEquals("Solde insuffisant pour cette transaction.", exception.getMessage());
	}

	private void createUtilisateursEtRelations(double soldeCompteDebiteur, double soldeCompteCrediteur, boolean isRelation) {
		Users userSender = new Users();
	        userSender.setUserId(1);
	        
	        Users userReceiver = new Users();
	        userReceiver.setUserId(2);
	        
	        Compte senderCompte = new Compte();
	        senderCompte.setSoldeCompte(soldeCompteDebiteur);
	        
	        Compte receiverCompte = new Compte();
	        receiverCompte.setSoldeCompte(soldeCompteCrediteur);

	        when(usersRepository.findById(1)).thenReturn(Optional.of(userSender));
	        when(usersRepository.findById(2)).thenReturn(Optional.of(userReceiver));
	        when(compteRepository.findByUserCompteId(userSender)).thenReturn(Optional.of(senderCompte));
	        when(compteRepository.findByUserCompteId(userReceiver)).thenReturn(Optional.of(receiverCompte));
	        when(usersLinkRepository.existsByUserSenderIdAndUserRecieverId(userSender, userReceiver)).thenReturn(isRelation);
	}
	private Transactions createTransaction(Users userSender, Users userReciever, int transactionId, double montant, String description ) {
		Transactions transaction = new Transactions();
        transaction.setUserSenderId(userSender);
        transaction.setUserRecieverId(userReciever);
        transaction.setTransactionId(transactionId);
        transaction.setMontant(montant);
        transaction.setDescription(description);
		return transaction;
	}

}
