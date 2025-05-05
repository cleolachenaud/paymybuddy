package com.openclassroom.paymybuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;

public interface ITransactionsRepository extends CrudRepository<Transactions, Integer> {
	
	List<Transactions> findAllByUserSender(Users userSender);
	
	Optional<Transactions> findByUserSenderId(Users userSenderId); 

}
