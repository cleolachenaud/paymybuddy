package com.openclassroom.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Transactions;

public interface ITransactionsRepository extends CrudRepository<Transactions, Integer> {
	
	public Iterable<Transactions> findAllByUser(int userId);

}
