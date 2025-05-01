package com.openclassroom.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Transactions;
import com.openclassroom.paymybuddy.model.Users;
import com.openclassroom.paymybuddy.repository.TransactionsRepository;

@Service
public class TransactionsService {
	
	@Autowired
	TransactionsRepository transactionsRepository;
	
	public Iterable<Transactions> getTransactions(){
		return transactionsRepository.findAll();
	}
	
	public Optional<Transactions> getTransactionById(int id) {
		return transactionsRepository.findById(id);
	}
	
	public Transactions saveTransaction (Transactions transaction) {
		return transactionsRepository.save(transaction);
	}
	
	public void deleteTransaction (Transactions transaction) {
		transactionsRepository.delete(transaction);
	}
}
