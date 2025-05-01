package com.openclassroom.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Users;

public interface UsersRepository extends CrudRepository<Users, Integer> {
	
	public Users findByUsername (String username);

}
