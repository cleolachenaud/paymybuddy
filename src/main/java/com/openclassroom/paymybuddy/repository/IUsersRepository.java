package com.openclassroom.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Users;

public interface IUsersRepository extends CrudRepository<Users, Integer> {
	
	public Users findByEmail (String email);
	
	public Users findByUsername (String username);
		
	public Boolean existsByEmail(String email);

	public Optional<Users> findById (int userId);

}
