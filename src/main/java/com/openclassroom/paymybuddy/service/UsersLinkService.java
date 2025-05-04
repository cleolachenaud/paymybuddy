package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;
import com.openclassroom.paymybuddy.repository.IUsersLinkRepository;
import com.openclassroom.paymybuddy.repository.IUsersRepository;

@Service
public class UsersLinkService {

	@Autowired
	IUsersLinkRepository usersLinkRepository;
	
	@Autowired
	IUsersRepository usersRepository;
	
	public Iterable<UsersLink> getUsers(){
		return usersLinkRepository.findAll();
	}
	
	@Transactional
    public void addUsersLink(String emailAdd, int userSender) {
		
		 // je vérifie que les utilisateurs existent sur l'application
        Users userSenderId = usersRepository.findById(userSender);
        if(userSenderId == null) {
        	 throw new RuntimeException("l'utilisateur n'existe pas");
        }
        
        Users userReceiverId = usersRepository.findByEmail(emailAdd);
        if(userReceiverId == null) {
       	 throw new RuntimeException("la personne que vous recherchez n'existe pas");
       }

        // j'ajoute la relation entre les deux 
        UsersLink relationship = new UsersLink(userSenderId, userReceiverId);    
        
        // j'enregistre la relation
        usersLinkRepository.save(relationship);
        
    }
	
}
