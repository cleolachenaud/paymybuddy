package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	UsersRepository usersRepository;
	
	public Iterable<Users> getUsers(){
		return usersRepository.findAll();
	}

	public Optional<Users> getUsersById(int id) {
		return usersRepository.findById(id);
	}
	
	public Users saveUser (Users user) {
		return usersRepository.save(user);
	}
	
	public void deleteUser (Users user) {
		usersRepository.delete(user);
	}
}
