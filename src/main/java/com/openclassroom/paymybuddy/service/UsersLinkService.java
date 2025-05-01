package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;
import com.openclassroom.paymybuddy.repository.UsersLinkRepository;

@Service
public class UsersLinkService {

	@Autowired
	UsersLinkRepository usersLinkRepository;
	
	public Iterable<UsersLink> getUsers(){
		return usersLinkRepository.findAll();
	}
	
	public Optional<UsersLink> getUsersLinkById(int id) {
		return usersLinkRepository.findById(id);
	}
	
	public UsersLink saveUsersLink (UsersLink usersLink) {
		return usersLinkRepository.save(usersLink);
	}
	
	public void deleteUsersLink (UsersLink usersLink) {
		usersLinkRepository.delete(usersLink);
	}
	
}
