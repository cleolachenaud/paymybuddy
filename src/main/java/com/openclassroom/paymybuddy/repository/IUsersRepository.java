package com.openclassroom.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Users;

public interface IUsersRepository extends CrudRepository<Users, Integer> {
	
	public Users findByUsername (String username);
	
	public Users findByEmail (String email);
	
	public Users findById(int id);
	
	public Boolean existsByEmail(String email);
	
	Optional<Users> findById(Users userSenderId);

}
