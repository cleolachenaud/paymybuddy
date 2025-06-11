package com.openclassroom.paymybuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;

public interface IUsersLinkRepository extends CrudRepository<UsersLink, Integer> {
	
	boolean existsByUserSenderIdAndUserRecieverId(Users userSenderId, Users userRecieverId);
	
	Optional<UsersLink> findByUserSenderId(Users userSenderId);
	
	List<UsersLink> findAllByUserSenderId(Users userSenderId);

}
