package com.openclassroom.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.paymybuddy.model.Compte;

@Repository
public interface CompteRepository extends CrudRepository<Compte, Integer> {

}
