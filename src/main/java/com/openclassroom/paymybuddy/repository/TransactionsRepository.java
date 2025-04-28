package com.openclassroom.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassroom.paymybuddy.model.Transactions;

public interface TransactionsRepository extends CrudRepository<Transactions, Integer> {

}
