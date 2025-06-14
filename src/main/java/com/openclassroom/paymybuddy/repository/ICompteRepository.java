package com.openclassroom.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.paymybuddy.model.Compte;
import com.openclassroom.paymybuddy.model.Users;

@Repository
public interface ICompteRepository extends CrudRepository<Compte, Integer> {

	Optional<Compte> findByUserCompteId(Users userCompteId); 

}
