package com.openclassroom.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.model.UsersLink;

public interface IUsersLinkRepository extends CrudRepository<UsersLink, Integer> {
	
	boolean existsByUser1AndUser2(Users userSenderId, Users userRecieverId);

}
